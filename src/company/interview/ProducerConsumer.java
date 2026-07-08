package company.interview;

import java.util.ArrayDeque;
import java.util.Queue;

public class ProducerConsumer {

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();
        SharedResources resources = new SharedResources(queue, 3);
        resources.getQueue().offer(1);

        System.out.println("queue size is "+ queue.size());

        Thread producer =  new Thread(new Producer(resources));
        Thread consumer = new Thread(new Consumer(resources));

        producer.start();
        consumer.start();

    }
}

class Producer implements Runnable{

    private final SharedResources sharedResources;

    public Producer(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    @Override
    public void run() {
        System.out.println("producer thread "+ Thread.currentThread().getName());

        while(true) {

            synchronized (sharedResources) {
                while (sharedResources.getSize() <= sharedResources.getQueue().size()) {
                    try {
                        sharedResources.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                sharedResources.getQueue().add(1);
                System.out.println("producer added value ... "+ sharedResources.getQueue().peek() +"queue size is ..."+ sharedResources.getQueue().size());
                sharedResources.notifyAll();
            }
        }
    }
}

class Consumer implements Runnable{

    private final SharedResources sharedResources;

    public Consumer(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    @Override
    public void run() {
        System.out.println("consumer thread "+Thread.currentThread().getName());

        while (true) {
            synchronized (sharedResources) {
                while (sharedResources.getQueue().isEmpty()) {

                    try {
                        sharedResources.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("consumed value is ...." + sharedResources.getQueue().poll() +"size is .."+ sharedResources.getQueue().size());
                sharedResources.notifyAll();
            }
        }
    }
}

class SharedResources {

    private Queue<Integer> queue;
    private int size;

    public SharedResources(Queue<Integer> queue, int size) {
        this.queue = queue;
        this.size = size;
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "SharedResources{" +
                "queue=" + queue +
                ", size=" + size +
                '}';
    }
}