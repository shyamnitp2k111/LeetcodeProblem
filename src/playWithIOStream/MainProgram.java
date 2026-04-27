package playWithIOStream;

import java.io.*;
import java.lang.ProcessHandle;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainProgram {

    public static void main(String[] args) throws IOException {

        // Create a stream pipe: OutputStream to InputStream
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        // Redirect System.out to your custom OutputStream
        PrintStream ps = new PrintStream(out, true);
        System.setOut(ps);

        // Separate thread to read output in real-time
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println("Captured: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

/*        PrintStream printStream = new PrintStream("MainLogs");
        System.setOut(printStream);*/
        System.out.println("Shyam");

        ThreadClass threadClass = new ThreadClass();
        threadClass.start();


       // OutputStream stressStdoutPrintStream = Files.newOutputStream(new File("MainLogs").toPath());



        /*PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);

        InputStreamDrainer outDrainer = new InputStreamDrainer(
                pis,
                new OutputStream[]{stressStdoutPrintStream},
                new OutputStream[]{System.out},
                "[stress.process.out] "
        );

        List<String> regexps = new ArrayList<>();
        regexps.add("Inside thread ....");
        outDrainer.setExpectedPatterns(regexps);

        outDrainer.start();*/



    }
}
