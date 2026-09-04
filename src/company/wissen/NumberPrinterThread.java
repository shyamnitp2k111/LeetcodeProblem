package company.wissen;

public class NumberPrinterThread {

    public static void main(String[] args) throws InterruptedException {

        NumberPrinter printer = new NumberPrinter();

        Thread t1 = new Thread(printer.getTask(1), "Thread-1");
        Thread t2 = new Thread(printer.getTask(2), "Thread-2");
        Thread t3 = new Thread(printer.getTask(3), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Completed");
    }
}

class NumberPrinter {

    private int number = 1;

    private final Object lock = new Object();

    Runnable getTask(int threadNumber) {

        return () -> {

            while (true) {

                synchronized (lock) {

                    if (number > 29) {
                        lock.notifyAll();
                        return;
                    }

                    if (number % 3 == threadNumber % 3) {

                        System.out.println(
                                Thread.currentThread().getName()
                                        + " : "
                                        + number
                        );

                        number++;

                        lock.notifyAll();

                    } else {

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }
        };
    }
}