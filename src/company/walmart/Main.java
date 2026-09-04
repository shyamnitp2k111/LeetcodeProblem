package company.walmart;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        CustomThreadPoolExecutor executor =
                new CustomThreadPoolExecutor(
                        2,              // corePoolSize
                        4,              // maxPoolSize
                        5,              // queue capacity
                        2,              // keep alive
                        TimeUnit.SECONDS
                );

        for (int i = 1; i <= 10; i++) {

            int taskId = i;

            try {

                executor.execute(() -> {

                    System.out.println(Thread.currentThread().getName()
                                    + " executing Task "
                                    + taskId);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " rejected");
            }
        }

        executor.shutdown();

        executor.awaitTermination();

        System.out.println("All tasks completed.");
    }
}