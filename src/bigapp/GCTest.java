package bigapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/*
 * @test
 * @bug 6362557 8200698
 * @summary test GC
 * @author Shyam Kishor
 */
public class GCTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + "/bin/java";

        String[] command = new String[] {
                javaBin,
                "-XX:+IgnoreUnrecognizedVMOptions",
                //"-XX:+UnlockExperimentalVMOptions",
                "-XX:+UseG1GC",
                GCTestClass.class.getName()
        };
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        // Read stdout in a separate thread
        Thread stdoutReader = new Thread(() -> {
            try (InputStream is = process.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[STDOUT] " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Read stderr in a separate thread
        Thread stderrReader = new Thread(() -> {
            try (InputStream is = process.getErrorStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println("[STDERR] " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stdoutReader.start();
        stderrReader.start();

        // Wait for process to finish
        int exitCode = process.waitFor();

        // Wait for readers to finish
        stdoutReader.join();
        stderrReader.join();

        System.out.println("Process exited with code " + exitCode);
    }
}


class GCTestClass {

    public static void main(String[] args) {
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            System.out.println("GC Name: " + gc.getName());
            System.err.println("GC Name: " + gc.getName());
        }

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        for (String arg : runtimeMxBean.getInputArguments()) {
            System.out.println("VM Argument: " + arg);
        }
    }
}