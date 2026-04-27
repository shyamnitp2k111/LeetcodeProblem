package OutPrintDrainer;

public class ThreadRunning extends Thread{
    @Override
    public void run() {

        System.out.println("Inside thread ....");

    }

    public static void main(String[] args) {
        System.out.println("Inside main method .....");
        new ThreadRunning().start();
    }
}
