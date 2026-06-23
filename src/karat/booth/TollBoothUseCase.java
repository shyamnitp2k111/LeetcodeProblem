package karat.booth;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;


/**
We are writing software to analyze logs for toll booths on a highway. This highway is a divided highway with limited access; the only way on to or off of the highway is through a toll booth.

There are three types of toll booths:
* ENTRY (E in the diagram) toll booths, where a car goes through a booth as it enters the highway.
* EXIT (X in the diagram) toll booths, where a car goes through a booth as it exits the highway.
* MAINROAD (M in the diagram), which have sensors that record a license plate as a car drives through at full speed.


        Exit Booth                         Entry Booth
            |                                   |
            X                                   E
             \                                 /
---<------------<---------M---------<-----------<---------<----
                                         (West-bound side)

===============================================================

                                         (East-bound side)
------>--------->---------M--------->--------->--------->------
             /                                 \
            E                                   X
            |                                   |
        Entry Booth                         Exit Booth

For our first task:
1-1) Read through and understand the code and comments below. Feel free to run the code and tests.
1-2) The tests are not passing due to a bug in the code. Make the necessary changes to LogEntry to fix the bug.

We are interested in how many people are using the highway, and so we would like to count how many complete journeys are taken in the log file.

A complete journey consists of:
* A driver entering the highway through an ENTRY toll booth.
* The driver passing through some number of MAINROAD toll booths (possibly 0).
* The driver exiting the highway through an EXIT toll booth.

For example, the following excerpt of log lines contains complete journeys for the cars with JOX304 and THX138:

.
.
.
90750.191 JOX304 250E ENTRY
91081.684 JOX304 260E MAINROAD
91082.101 THX138 110E ENTRY
91483.251 JOX304 270E MAINROAD
91873.920 THX138 120E MAINROAD
91874.493 JOX304 280E EXIT
.
.
91982.102 THX138 290E EXIT
92301.302 THX138 300E ENTRY
92371.302 THX138 310E EXIT
.

→ This log contains 3 complete journeys:
  • JOX304: 1 journey
  • THX138: 2 journeys

You may assume that the log only contains complete journeys, and there are no missing entries.

2-1) Write a function in LogFile named countJourneys() that returns how many
     complete journeys there are in the given LogFile.

We would like to catch people who are driving at unsafe speeds on the highway. To help us do that, we would like to identify journeys where a driver does either of the following:
* Drive 130 km/h or greater in any individual 10km segment of tollway.
* Drive 120 km/h or greater in any two 10km segments of tollway.


For example, consider the following journey:
1000.000 TST002 270W ENTRY
1275.000 TST002 260W EXIT


In this case, the driver of TST002 drove 10 km in 275 seconds. We can calculate
that this driver drove an average speed of ~130.91km/hr over this segment:


10 km * 3600 sec/hr
------------------- = 130.91 km/hr
      275 sec


Note that:
* A license plate may have multiple journeys in one file, and if they drive at unsafe speeds in both journeys, both should be counted.
* We do not mark speeding if they are not on the highway (i.e. for any driving between an EXIT and ENTRY event).
* Speeding is only marked once per journey. For example, if there are 4 segments 120km/h or greater, or multiple segments 130km/h or greater, the journey is only counted once.


3-1) Write a function catchSpeeders in LogFile that returns a collection of license plates that drove at unsafe speeds during a journey in the LogFile.
     If the same license plate drives at unsafe speeds during two different journeys, the license plate should appear twice (once for each journey they drove at unsafe speeds).
*/


public class TollBoothUseCase {

    // public static void main(String[] args) {

    //   testLogEntry();
    // testLogFile();
//        testCountJourneys();
//        testCatchSpeeder();
    //}

    // ======================================================
    // LogEntry Class
    // ======================================================
    static class LogEntry {

        double timestamp;
        String licensePlate;
        String boothType;
        int location;
        String direction;

        public LogEntry(String logLine) {

            String[] parts = logLine.split("\\s+");

            this.timestamp = Double.parseDouble(parts[0]);
            this.licensePlate = parts[1];

            this.location = Integer.parseInt(
                    parts[2].substring(0, parts[2].length() - 1));

            this.direction = parts[2].substring(
                    parts[2].length() - 1);

            this.boothType = parts[3];
        }

        public String toString() {
            return timestamp + " " + licensePlate + " " +
                    location + direction + " " + boothType;
        }
    }

    // ======================================================
    // LogFile Class
    // ======================================================
    static class LogFile {

        List<LogEntry> logEntries;

        public LogFile() {
            logEntries = new ArrayList<>();
        }

        public LogFile(List<String> lines) {

            logEntries = new ArrayList<>();

            for (String line : lines) {
                logEntries.add(new LogEntry(line));
            }
        }

        public void addLogEntry(String line) {
            logEntries.add(new LogEntry(line));
        }

        public int size() {
            return logEntries.size();
        }


        /**
         * Approach:
         * 1. A journey is considered complete when:
         *    ENTRY → (optional MAINROAD) → EXIT happens for the same vehicle.
         *
         * 2. Use a HashMap to track active journeys:
         *    - Key: license plate
         *    - Value: direction (E/W)
         *
         * 3. Traverse each log entry:
         *    - If ENTRY:
         *         → store plate + direction in map (journey started)
         *
         *    - If EXIT:
         *         → check if plate exists in map
         *         → verify direction matches ENTRY direction
         *         → if valid:
         *              - increment count
         *              - remove plate from map (journey completed)
         *
         * 4. Ignore incomplete journeys (no EXIT).
         *
         * Time Complexity: O(n) where n = total number of log entries
         * Space Complexity: O(m) where m = active cars
         */
        public int countJourneys() {
            Map<String, String> activeJourneys = new HashMap<>();
            int count = 0;

            for (LogEntry entry : logEntries) {
                String plate = entry.licensePlate;

                if (entry.boothType.equals("ENTRY")) {
                    activeJourneys.put(plate, entry.direction);
                } else if (entry.boothType.equals("EXIT")) {
                    if (activeJourneys.containsKey(plate)) {
                        String entryDirection = activeJourneys.get(plate);
                        if (entryDirection.equals(entry.direction)) {
                            count++;
                            activeJourneys.remove(plate);
                        }
                    }
                }
            }

            return count;
        }


        /**
         * Approach:
         * 1. We need to detect unsafe journeys based on:
         *    - Any segment with speed >= 130 km/h  → unsafe immediately
         *    - OR at least 2 segments with speed >= 120 km/h
         *
         * 2. Use multiple maps to track journey state:
         *
         *    activeEntry:
         *       → tracks vehicles currently on highway
         *
         *    lastCheckpoint:
         *       → stores last log (ENTRY/MAINROAD) to calculate segment speed
         *
         *    segment120Count:
         *       → counts number of segments where speed >= 120 km/h
         *
         *    markedUnsafe:
         *       → tracks vehicles already marked unsafe for current journey
         *
         * 3. Traverse each log entry:
         *
         *    - If ENTRY:
         *        → initialize tracking for this vehicle
         *
         *    - If MAINROAD or EXIT:
         *        → check if vehicle is active
         *        → calculate:
         *              time = current.timestamp - previous.timestamp
         *              distance = difference in location
         *              speed = (distance * 3600) / time
         *
         *        → Apply rules:
         *              - speed >= 130 → mark unsafe
         *              - speed >= 120 → increment count;
         *                               if count >= 2 → mark unsafe
         *
         *        → update lastCheckpoint
         *
         *    - If EXIT:
         *        → if marked unsafe → increment unsafe journey count
         *        → cleanup all maps (journey ends)
         *
         * 4. Return total unsafe journeys
         *
         * Time Complexity: O(n) where n = total number of log entries
         * Space Complexity: O(m) where m = active cars
         */

        public int catchSpeeders() {

            Map<String, LogEntry> activeEntry = new HashMap<>();
            Map<String, LogEntry> lastCheckpoint = new HashMap<>();
            Map<String, Integer> segment120Count = new HashMap<>();
            Set<String> markedUnsafe = new HashSet<>();

            int unsafeCount = 0;

            for (LogEntry entry : logEntries) {
                String plate = entry.licensePlate;

                if (entry.boothType.equals("ENTRY")) {
                    activeEntry.put(plate, entry);
                    lastCheckpoint.put(plate, entry);
                    segment120Count.put(plate, 0);
                    markedUnsafe.remove(plate);
                } else if (entry.boothType.equals("MAINROAD") || entry.boothType.equals("EXIT")) {

                    if (activeEntry.containsKey(plate)) {
                        LogEntry previous = lastCheckpoint.get(plate);
                        double time = entry.timestamp - previous.timestamp;
                        int distance = Math.abs(entry.location - previous.location);

                        if (distance > 0 && time > 0) {
                            double speed = (distance * 3600.0) / time;
                            if (speed >= 130.0) {
                                markedUnsafe.add(plate);
                            } else if (speed >= 120.0) {
                                int count = segment120Count.get(plate) + 1;
                                segment120Count.put(plate, count);
                                if (count >= 2) {
                                    markedUnsafe.add(plate);
                                }
                            }
                        }
                        lastCheckpoint.put(plate, entry);
                        if (entry.boothType.equals("EXIT")) {
                            if (markedUnsafe.contains(plate)) {
                                unsafeCount++;
                            }
                            activeEntry.remove(plate);
                            lastCheckpoint.remove(plate);
                            segment120Count.remove(plate);
                            markedUnsafe.remove(plate);
                        }
                    }
                }
            }
            return unsafeCount;
        }
    }

    // ======================================================
    // Test Methods
    // ======================================================

    @Test
    public void testLogEntry() {

        System.out.println("Running testLogEntry...");

        LogEntry entry =
                new LogEntry("34400.409 SXY288 210E ENTRY");
//        LogEntry entry1 =
//                new LogEntry(" ");

        System.out.println(entry);
        System.out.println();
    }

    @Test
    public void testLogFile() {

        System.out.println("Running testLogFile...");

        LogFile logFile = new LogFile();

        logFile.addLogEntry("10000.100 ABC123 10E ENTRY");
        logFile.addLogEntry("10020.100 ABC123 20E EXIT");

        System.out.println("Log size: " + logFile.size());
        Assert.assertEquals(2, logFile.size());
        System.out.println();
    }

    @Test
    public void testCountJourneys() {

        System.out.println("Running testCountJourneys...");


        List<String> logs = Arrays.asList(
                "10000.000 CAR1 10E ENTRY",
                "10100.000 CAR1 20E EXIT",
                "11000.000 CAR2 50W ENTRY",
                "11100.000 CAR2 60W EXIT",
                "12000.000 CAR3 10E ENTRY" // incomplete
        );

        LogFile logFile = new LogFile(logs);

        System.out.println("Total Journeys: "
                + logFile.countJourneys());

        System.out.println();
    }

    @Test
    public void testCatchSpeeder() {

        System.out.println("Running testCatchSpeeder...");

        List<String> logs = Arrays.asList(

                // 130+ km/h segment
                "1000.000 TST002 270W ENTRY",
                "1275.000 TST002 260W EXIT",

                // Two 120+ segments
                "2000.000 CAR001 100E ENTRY",
                "2250.000 CAR001 110E MAINROAD",
                "2500.000 CAR001 120E EXIT",

                // Safe journey
                "3000.000 SAFE01 10E ENTRY",
                "3600.000 SAFE01 20E EXIT"
        );

        LogFile logFile = new LogFile(logs);

        System.out.println("Unsafe Journeys: "
                + logFile.catchSpeeders());

        System.out.println();
    }
}