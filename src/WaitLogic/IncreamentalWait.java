package WaitLogic;

import java.io.File;

public class IncreamentalWait {

    public static void main(String[] args) {

       // long incrementalWaitDuration = 30 ;
        long waitingTime = 30;
       // int index = 1;

        File file = new File("shyam");
        while(!file.exists() || file.length() == 0) {
            System.err.println( "stress.process.properties file not found or No file content so waiting " + waitingTime);
            if (waitingTime > 9000) {
                System.err.println("Timeout reached. stress.process.properties file not found or No file content");
                throw new RuntimeException("stress.process.properties file not found or No file content");
            }
            try {
                waitingTime = waitingTime * 2;
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
