package bigapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class FileOperation {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("stress.process.shutdown.port",String.valueOf(123456));
        try (FileOutputStream fos = new FileOutputStream(new File("/Users/skishor/Documents/SampleProject/test"))) {
            properties.store(fos, "The shutdown port value.");
        }

        System.out.println("time "+ new Date().getTime());

        //File file = new File("/Users/skishor/Documents/SampleProject/test1");
       // waitTillFileGetCreate();
    }

    private static void waitTillFileGetCreate() {

        File file = new File("/Users/skishor/Documents/SampleProject/shyam");
        long startTime = System.currentTimeMillis();

        while(!file.exists()) {
            System.err.println("stress.process.properties file is not found so wait ..." + new Date());
            if ((System.currentTimeMillis() - startTime) > 300000) {
                System.out.println("Timeout reached. The stress.process.properties file was not found.");
                break;
            }
            try {
                // Sleep for 100ms before checking again
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(" file created " + file.exists());
    }
}
