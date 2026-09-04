package company;

import java.util.concurrent.atomic.AtomicInteger;

public class Interview {

    //1
    //2
    //3 - thread 3


    static void main() throws InterruptedException {

        ThreadFactory threadFactory = new ThreadFactory();
        Thread thread1 = new Thread(threadFactory.getThread1());
        Thread thread2 = new Thread(threadFactory.getThread2());
        Thread thread3 = new Thread(threadFactory.getThread3());


        thread1.start();
        thread2.start();
        thread3.start();


        thread1.join();
        thread2.join();
        thread3.join();

    }

}

class ThreadFactory {

    AtomicInteger atomicInteger = new AtomicInteger(1);
    Object object = new Object();



    Runnable getThread1() {
        Runnable runnable = () -> {


            synchronized (object) {
                while (true) {

                    if ( atomicInteger.get() == 30) {
                        break;
                    }

                    if (atomicInteger.get() % 3 == 1) {
                       // System.out.println("value is ... " + atomicInteger.get());
                        System.out.println("value is ... " + 1);

                        atomicInteger.getAndIncrement();
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };

        return runnable;
    }

    Runnable getThread2() {
        Runnable runnable = () -> {


            synchronized (object) {


                while (true) {

                    if ( atomicInteger.get() == 30) {
                        break;
                    }


                    if (atomicInteger.get() % 3 == 2) {
                       // System.out.println("value is ... " + atomicInteger.get());

                        System.out.println("value is ... " + 2);
                        atomicInteger.getAndIncrement();
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };

        return runnable;
    }

    Runnable getThread3() {
        Runnable runnable = () -> {


            synchronized (object) {
                while (true) {

                    if ( atomicInteger.get() == 30) {
                        break;
                    }

                    if (atomicInteger.get() % 3 == 0) {
                       // System.out.println("value is ... " + atomicInteger.get());

                        System.out.println("value is ... " + 3);
                        atomicInteger.getAndIncrement();
                        object.notifyAll();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };

        return runnable;
    }
}
