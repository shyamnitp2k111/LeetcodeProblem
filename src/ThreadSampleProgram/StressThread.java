package ThreadSampleProgram;

public class StressThread extends Thread{

    @Override
    public void run() {
        try {
            System.out.println("Before Stress thread");
            Thread.sleep(10000000);
            System.out.println("After Stress thread");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
