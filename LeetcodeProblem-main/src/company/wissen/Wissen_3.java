package company.wissen;

import java.util.concurrent.atomic.AtomicInteger;

public class Wissen_3 {
    /*

      3 three thread  - 1 to 10
      thread - 1

     */
    static void main() {


        AtomicInteger number = new AtomicInteger(1);
        Object object = new Object();
        Runnable runnable1 = () -> {

            while(true) {

                synchronized (object) {

                    if(number.get() == 10) {
                        break;
                    }
                    if (number.get() % 3 == 1) {

                        synchronized (object) {
                            System.out.println("Number is .. " + number);
                            number.getAndIncrement();
                            object.notifyAll();
                        }
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

        Runnable runnable2 = () -> {

            while(true) {

                synchronized (object) {

                    if(number.get() == 10) {
                        break;
                    }
                    if (number.get() % 3 == 2) {


                        System.out.println("Number is .. " + number);
                        number.getAndIncrement();
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

        Runnable runnable3 = () -> {



            while(true) {

                if(number.get() == 10) {
                    break;
                }

                synchronized (object) {
                    if (number.get() % 3 == 0) {


                        System.out.println("Number is .. " + number);
                        number.getAndIncrement();
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


        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);

        thread1.start();
        thread2.start();
        thread3.start();



    }
}
