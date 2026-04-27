package OutPrintDrainer;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class OutPrintsClass {

    public static void main(String[] args) throws IOException, InterruptedException {

        PrintStream outfile = new PrintStream(new FileOutputStream("stress.dump.out"));
        System.setOut(outfile);

        System.out.println("Initial point ....... Ram Je ..........     ");

        OutputStream stressStderrPrintStream = Files.newOutputStream(new File("log").toPath());

        //ProcessBuilder pb = new ProcessBuilder("pwd").directory(new File("."));
        ProcessBuilder pb = new ProcessBuilder(
                "/Users/skishor/Documents/JDK/jdk-21.0.6+3.jdk/Contents/Home/bin/java",
                "-cp",
                "/Users/skishor/Documents/SampleProject/classes/OutPrintDrainer",
                "ThreadRunning"
        );

        Process process = pb.start();

        process.waitFor();



        System.out.println("/Users/skishor");

        InputStreamDrainer outDrainer = new InputStreamDrainer(
                process.getInputStream(),
                new OutputStream[]{stressStderrPrintStream},
                new OutputStream[]{System.out},
                "[stress.process.out] "
        );

        List<String> regexps = new ArrayList<>();
        regexps.add("Inside thread ....");
        outDrainer.setExpectedPatterns(regexps);

        outDrainer.start();

        System.out.println("areAllPatternsFound is "+outDrainer.areAllPatternsFound());


        System.out.println("completed .......");
        System.out.println("areAllPatternsFound is "+outDrainer.areAllPatternsFound());
    }
}
