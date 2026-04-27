package testcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testSsh {

    public static void main(String[] args) throws IOException, InterruptedException {
        executeSSHCommand();
    }

    public static void executeSSHCommand() {
        String host = "slc18lsk.us.oracle.com";       // Replace with your host
        String user = "jpgansible";           // Replace with your username
        String password = "JpgOracle@123";   // Replace with your password
        String command = "pwd"; // Example command sequence

        // SSH command with sshpass
        String sshCommand = String.format("sshpass -p '%s' ssh -o StrictHostKeyChecking=no %s@%s \"%s\"",
                password, user, host, command);

        try {
            // Initialize ProcessBuilder with the command
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", sshCommand);
            builder.redirectErrorStream(true); // Combine stdout and stderr
            Process process = builder.start();

            // Read and output the command execution result
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print the command output
            }

            int exitCode = process.waitFor();
            System.out.println("Process finished with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
