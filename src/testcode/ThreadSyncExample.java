package testcode;

public class ThreadSyncExample {

    // Shared object to synchronize threads
   // private static final Object lock = new Object();

    // Child thread task
    static class ChildThread extends Thread {
        public void run() {
            try {
                System.out.println("Child thread started.");
                //Thread.sleep(1);  // Simulating some work
                System.out.println("Child thread reaching the particular line.");
                
                synchronized (LockerClass.lock) {
                    // Notify the main thread that the particular line has been reached
                    LockerClass.lock.notify();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ThreadSampleProgram.Main thread task
    public static void main(String[] args) {
        try {
            // Start the child thread

            ChildThreadStart childThreadStart = new ChildThreadStart();
            childThreadStart.method();

            Thread.sleep(10);
            // ThreadSampleProgram.Main thread will wait for the child to notify
            synchronized (LockerClass.lock) {
                System.out.println("ThreadSampleProgram.Main thread started.");
                LockerClass.lock.wait();  // Wait until the child thread calls notify()
                System.out.println("ThreadSampleProgram.Main thread continues after child thread reaches the particular line.");
            }

            System.out.println("ThreadSampleProgram.Main thread before join ");

            // Wait for the child thread to finish
           // child.join();

            System.out.println("ThreadSampleProgram.Main thread after join ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ChildThreadStart{
    void method(){
        ThreadSyncExample.ChildThread child = new ThreadSyncExample.ChildThread();
        child.start();
    }
}
