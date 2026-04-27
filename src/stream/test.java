package stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {

        InputStream fileInputStream1 = Files.newInputStream(Paths.get("ChildThreadLogs"));
        InputStream fileInputStream2 = Files.newInputStream(Paths.get("MainLogs"));
        Enumeration<InputStream> streams = Collections.enumeration(List.of(fileInputStream1, fileInputStream2));
        InputStream combined = new SequenceInputStream(streams);
        BufferedReader reader = new BufferedReader(new InputStreamReader(combined));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();

    }
}
