package testcode;

public class ExtractMach5Url {

    public static void main(String[] args) {
        String logs = "[jib LOG] Bootstrapping com.oracle.java.jib jib 3.0-SNAPSHOT 406b8ec5\n" +
                "[jib LOG] Mirror site ash\n" +
                "[jib LOG] JIB Java CLI Version: jib 3.0-SNAPSHOT Build: 0a62a07b Date: Mon May 05 20:20:13 UTC 2025\n" +
                "[jib LOG] Artifactory Instance: ash (main, phx, sca, llg, fra, bom)\n" +
                "[jib LOG] Artifactory Download: https://artifactory-cache-ash.java.oraclecorp.com/artifactory\n" +
                "[jib LOG] Artifactory Upload: https://jpg-data.us.oracle.com/artifactory\n" +
                "[jib LOG] BuildIdService: https://mach5.us.oracle.com:10055\n" +
                "[jib LOG] Data Dir: /var/tmp/jib-skishor\n" +
                "[jib LOG] Command: mach5\n" +
                "[jib LOG] Src Dir: /Users/skishor/Documents/workspace/JDK-Code/jdk/closed/bin/../..\n" +
                "[jib LOG] Downloading com/oracle/java/sparky/mach5/1.0.2958/mach5-1.0.2958-distribution.zip ... Checksum\n" +
                "[jib LOG] Installing com/oracle/java/sparky/mach5/1.0.2958/mach5-1.0.2958-distribution.zip ... Checksum\n" +
                "Username: shyam.kishor@oracle.com\n" +
                "Mach 5 Health State: green\n" +
                "\n" +
                "Creating job description... done\n" +
                "Creating build ID... 2025-06-26-1616134.shyam.kishor.jdk\n" +
                "Publishing source using JIB\n" +
                "[jib LOG] JIB Java CLI Version: jib 3.0-SNAPSHOT Build: 0a62a07b Date: Mon May 05 20:20:13 UTC 2025\n" +
                "[jib LOG] Command: publish-src\n" +
                "[jib LOG] Src Dir: /Users/skishor/Documents/workspace/JDK-Code/jdk\n" +
                "[jib LOG] BuildId: 2025-06-26-1616134.shyam.kishor.jdk [CMDLINE]\n" +
                "[jib LOG] Generating source/src-full.tar.gz ... Success\n" +
                "[jib LOG] Publishing jdk/personal/shyam.kishor/2025-06-26-1616134.shyam.kishor.jdk/source/src-full.tar.gz ... Success\n" +
                "[jib LOG] Generating source/src-conf.tar.gz ... Success\n" +
                "[jib LOG] Publishing jdk/personal/shyam.kishor/2025-06-26-1616134.shyam.kishor.jdk/source/src-conf.tar.gz ... Success\n" +
                "[jib LOG] Registering jdk/personal/shyam.kishor/2025-06-26-1616134.shyam.kishor.jdk/source/src-conf.tar.gz ... Success\n" +
                "[jib LOG] Registering jdk/personal/shyam.kishor/2025-06-26-1616134.shyam.kishor.jdk/source/src-full.tar.gz ... Success\n" +
                "\n" +
                "Requesting job ID... skishor-dev-20250626-1617-30253752\n" +
                "Submitting job... done\n" +
                "\n" +
                "Job id: skishor-dev-20250626-1617-30253752\n" +
                "Number of tasks: 10\n" +
                "\n" +
                "MDash: https://mach5.us.oracle.com/mdash/jobs/skishor-dev-20250626-1617-30253752\n";

       /* String url = logs.substring(logs.lastIndexOf("MDash:")+"MDash:".length()+1,logs.length()-1);
        System.out.println(url);*/

        String prefix = "MDash:";
        int startIndex = logs.lastIndexOf(prefix);
        if (startIndex != -1 && logs.length() > startIndex + prefix.length() + 1) {
            System.out.println(logs.substring(startIndex + prefix.length() + 1, logs.length() - 1));
        } else {
            // handle error or assign default value
            System.out.println("inside else");
        }


    }
}
