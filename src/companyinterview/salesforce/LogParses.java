package companyinterview.salesforce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*

Process Log Files

You are given a log file containing application log entries.

Each log entry follows the format:

Timestamp | Type | Message | IP

where:

Timestamp is the time when the event occurred.
Type is one of:
INFO
WARNING
ERROR
Message describes the event.
IP is the source IP address.
Task

Read each log entry from the input file and separate the logs into two output files:

errorFile
Contains all log entries whose type is ERROR.
normalFile
Contains all log entries whose type is not ERROR
        (INFO and WARNING logs).


Example

Input
2023-05-20 10:15:23 | INFO | Application started successfully | IP: 192.168.1.100

2023-05-20 12:30:45 | ERROR | Invalid input received | IP: 192.168.1.200

2023-05-20 15:18:12 | INFO | User logged in | IP: 192.168.1.50

2023-05-20 18:55:02 | WARNING | Low disk space detected | IP: 192.168.1.75

output :

errorFile
2023-05-20 12:30:45 | ERROR | Invalid input received | IP: 192.168.1.200


normalFile
2023-05-20 10:15:23 | INFO | Application started successfully | IP: 192.168.1.100

2023-05-20 15:18:12 | INFO | User logged in | IP: 192.168.1.50

2023-05-20 18:55:02 | WARNING | Low disk space detected | IP: 192.168.1.75

*/

public class LogParses {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        List<String> errorLogs = new ArrayList<>();
        List<String> normalLogs = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            String log = sc.nextLine();

            String[] parts = log.split("\\|");

            if (parts.length < 4) {
                continue;
            }

            String logType = parts[1].trim();

            if ("ERROR".equals(logType)) {
                errorLogs.add(log);
            } else {
                normalLogs.add(log);
            }
        }

        System.out.println("===== ERROR LOGS =====");
        errorLogs.forEach(System.out::println);

        System.out.println("\n===== NORMAL LOGS =====");
        normalLogs.forEach(System.out::println);

        sc.close();
    }
}