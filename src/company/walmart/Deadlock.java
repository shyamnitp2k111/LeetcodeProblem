package company.walmart;

public class Deadlock {

    static void main() {

        Object a = new Object();
        Object b = new Object();

        Thread customThreadOne = new Thread(new CustomThreadOne("Thread -1 ", a, b));
        Thread customThreadTwo = new Thread(new CustomThreadTwo("Thread -1 ", a, b));

        customThreadOne.start();
        customThreadTwo.start();
    }



}


class CustomThreadTwo implements Runnable {

    private Boolean flag = true;
    private String threadName;
    private Object a;
    private Object b;

    public CustomThreadTwo(String threadName, Object A, Object B ) {
        this.threadName = threadName;
        this.a = A;
        this.b = B;
    }

    @Override
    public void run() {
        synchronized (b) {


                try {

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            synchronized (a) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


class CustomThreadOne implements Runnable {

    private Boolean flag = true;

    private String threadName;
    private Object a;
    private Object b;

    public CustomThreadOne(String threadName, Object a, Object b) {
        this.threadName = threadName;
        this.a = a;
        this.b = b;

    }

    @Override
    public void run() {
        synchronized (a) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (b) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}