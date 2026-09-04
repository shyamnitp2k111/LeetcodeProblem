package company.walmart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTimeMs;

    private final BlockingQueue<Runnable> taskQueue;

    private final AtomicInteger currentPoolSize = new AtomicInteger(0);
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    /*
     * Access to workers is always protected by synchronized methods/blocks.
     */
    private final List<Worker> workers = new ArrayList<>();

    public CustomThreadPoolExecutor(int corePoolSize, int maxPoolSize,
            int queueCapacity, long keepAliveTime, TimeUnit unit) {

        if (corePoolSize <= 0) {
            throw new IllegalArgumentException("corePoolSize must be greater than 0");
        }

        if (maxPoolSize < corePoolSize) {
            throw new IllegalArgumentException("maxPoolSize must be >= corePoolSize");
        }

        if (queueCapacity <= 0) {
            throw new IllegalArgumentException("queueCapacity must be greater than 0");
        }

        if (keepAliveTime <= 0) {
            throw new IllegalArgumentException("keepAliveTime must be greater than 0");
        }

        if (unit == null) {
            throw new NullPointerException("TimeUnit cannot be null");
        }

        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTimeMs = unit.toMillis(keepAliveTime);

        this.taskQueue = new LinkedBlockingQueue<>(queueCapacity);
    }

    /**
     * Submit a task for execution.
     *
     * Execution policy:
     *
     * 1. Create worker if below corePoolSize.
     * 2. Otherwise try to enqueue.
     * 3. If queue is full, create worker up to maxPoolSize.
     * 4. Otherwise reject.
     */
    public void execute(Runnable task) {

        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }

        /*
         * First check.
         *
         * This prevents normal submissions after shutdown.
         */
        if (isShutdown.get()) {
            reject(task, "ThreadPool is shutdown");
        }

        /*
         * 1. Try to create a core worker.
         */
        if (currentPoolSize.get() < corePoolSize) {

            if (addWorker(task, true)) {
                return;
            }
        }

        /*
         * 2. Try to enqueue the task.
         */
        if (taskQueue.offer(task)) {

            /*
             * Shutdown may have happened concurrently
             * between the first check and queue insertion.
             *
             * If shutdown happened, remove the task if possible
             * and reject it.
             */
            if (isShutdown.get() && taskQueue.remove(task)) {
                reject(task, "ThreadPool is shutdown");
            }

            /*
             * Important:
             *
             * If there are no workers left, make sure somebody
             * processes the queued task.
             */
            if (currentPoolSize.get() == 0 && !isShutdown.get()) {
                addWorker(null, true);
            }

            return;
        }

        /*
         * 3. Queue is full.
         *
         * Try to create a non-core worker.
         */
        if (currentPoolSize.get() < maxPoolSize) {

            if (addWorker(task, false)) {
                return;
            }
        }

        /*
         * 4. Queue full + max workers reached.
         */
        reject(task, "Task queue is full and maxPoolSize is reached");
    }

    /**
     * Creates a worker safely.
     */
    private synchronized boolean addWorker(Runnable firstTask, boolean coreWorker) {

        if (isShutdown.get()) {
            return false;
        }

        int targetPoolSize = coreWorker ? corePoolSize : maxPoolSize;

        if (currentPoolSize.get() >= targetPoolSize) {
            return false;
        }

        Worker worker = new Worker(firstTask);

        workers.add(worker);

        currentPoolSize.incrementAndGet();

        worker.start();

        return true;
    }

    private void reject(Runnable task, String message) {
        throw new RejectedExecutionException(message);
    }

    /**
     * Graceful shutdown.
     *
     * No new tasks will be accepted.
     *
     * Already queued tasks will be processed.
     */
    public void shutdown() {

        if (!isShutdown.compareAndSet(false, true)) {
            return;
        }

        /*
         * Wake up workers that are blocked on queue.take().
         *
         * They will check shutdown + queue state and
         * continue processing queued tasks.
         */
        synchronized (this) {

            for (Worker worker : workers) {
                worker.interrupt();
            }
        }
    }

    /**
     * Returns whether executor is shutdown.
     */
    public boolean isShutdown() {
        return isShutdown.get();
    }

    /**
     * Returns number of currently running workers.
     */
    public int getPoolSize() {
        return currentPoolSize.get();
    }

    /**
     * Returns number of queued tasks.
     */
    public int getQueueSize() {
        return taskQueue.size();
    }

    /**
     * Worker thread.
     */
    private class Worker extends Thread {

        private Runnable firstTask;

        Worker(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {

            Runnable task = firstTask;
            firstTask = null;

            try {

                while (task != null || (task = getTask()) != null) {

                    try {
                        task.run();

                    } catch (Throwable throwable) {

                        /*
                         * One bad task should not kill the worker.
                         */
                        System.err.println(
                                "Task execution failed: "
                                        + throwable.getMessage()
                        );

                    } finally {
                        task = null;
                    }
                }

            } finally {

                processWorkerExit(this);
            }
        }

        /**
         * Gets the next task.
         */
        private Runnable getTask() {

            while (true) {

                /*
                 * Graceful shutdown:
                 *
                 * Don't exit until all queued tasks
                 * have been processed.
                 */
                if (isShutdown.get() && taskQueue.isEmpty()) {
                    return null;
                }

                /*
                 * Workers beyond corePoolSize are allowed
                 * to terminate after being idle.
                 */
                boolean allowTimeout =
                        currentPoolSize.get() > corePoolSize;

                try {

                    if (allowTimeout) {

                        Runnable task = taskQueue.poll(keepAliveTimeMs, TimeUnit.MILLISECONDS);

                        /*
                         * No task within keepAliveTime.
                         *
                         * Worker terminates.
                         */
                        if (task == null) {
                            return null;
                        }

                        return task;

                    } else {

                        /*
                         * Core worker waits indefinitely.
                         */
                        return taskQueue.take();
                    }

                } catch (InterruptedException e) {

                    /*
                     * Shutdown was requested.
                     *
                     * Don't immediately terminate if tasks
                     * are still waiting in the queue.
                     */
                    if (isShutdown.get()) {

                        if (taskQueue.isEmpty()) {
                            return null;
                        }

                        /*
                         * Queue still has tasks.
                         * Continue processing.
                         */
                        continue;
                    }

                    /*
                     * Spurious/non-shutdown interrupt.
                     * Continue waiting.
                     */
                }
            }
        }
    }

    /**
     * Removes terminated worker.
     */
    private synchronized void processWorkerExit(Worker worker) {
        workers.remove(worker);
        currentPoolSize.decrementAndGet();
    }

    /**
     * Wait until all workers terminate.
     */
    public void awaitTermination()
            throws InterruptedException {

        while (currentPoolSize.get() > 0) {

            synchronized (this) {

                if (currentPoolSize.get() == 0) {
                    return;
                }

                this.wait(100);
            }
        }
    }
}