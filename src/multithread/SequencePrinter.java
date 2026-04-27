package multithread;

public class SequencePrinter {
    private static final int LIMIT = 100;
    private static int number = 1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // Create three threads with assigned remainders
        Thread t1 = new Thread(new PrinterTask(1), "Thread-1");
        Thread t2 = new Thread(new PrinterTask(2), "Thread-2");
        Thread t3 = new Thread(new PrinterTask(0), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class PrinterTask implements Runnable {
        private final int remainder;

        PrinterTask(int remainder) {
            this.remainder = remainder;
        }

        @Override
        public void run() {
            while (number <= LIMIT) {
                synchronized (lock) {
                    // Wait until it's this thread's turn
                    while (number <= LIMIT && number % 3 != remainder) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    // Check limit again after waking up
                    if (number <= LIMIT) {
                        System.out.println(Thread.currentThread().getName() + ": " + number);
                        number++;
                        lock.notifyAll(); // Signal other threads to check their turn
                    }
                }
            }
        }
    }
}
