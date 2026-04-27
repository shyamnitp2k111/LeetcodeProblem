package playWithIOStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class ThreadClass extends Thread{
    @Override
    public void run() {
        try {
            PrintStream printStream = new PrintStream(new File("ChildThreadLogs"));

            System.setOut(printStream);
            System.out.println("Inside run method .......");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
