package ThreadSampleProgram;

import java.util.concurrent.CountDownLatch;

public final class Main {

    public static volatile CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        // Create and start the main thread logic
        /*Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("ThreadSampleProgram.Main thread: Reached synchronization point.");
                    Thread.sleep(20000); // Simulate some work in main thread
                    ChildThread.latch.await();  // Wait for the main thread to signal
                    System.out.println("ThreadSampleProgram.Main thread: Continuing after synchronization point.");
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        });*/


        // Start stress threads
        StressThread stressThread = new StressThread();
        stressThread.start();

        // Start both threads
        ChildThread childThread = new ChildThread();
        childThread.start();

         System.out.println("Before wait in main ..........."+countDownLatch.getCount());
         countDownLatch.await();
         System.out.println("After wait in main ..........."+countDownLatch.getCount());

        /*// Wait for both threads to finish
        childThread.join();
        mainThread.join();*/

        System.out.println("Both threads have finished.");
    }
}
