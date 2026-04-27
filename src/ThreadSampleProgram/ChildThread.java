package ThreadSampleProgram;

import java.util.concurrent.CountDownLatch;

public final class ChildThread extends  Thread{


    @Override
    public void run() {
        try {
            System.out.println("Child thread: Reached synchronization point."+ Main.countDownLatch.getCount());
            Thread.sleep(0);

            Main.countDownLatch.countDown();  // Signal the child thread to continue
            System.out.println("Child thread: Continuing after synchronization point." + Main.countDownLatch.getCount());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
