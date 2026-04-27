package testcode;

import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SystemClockVerifier implements Runnable {
    private final static String logFileName = "clock.verification.log";
    private final long checkInterval = 1;

    private volatile boolean shouldStop = false;
    private volatile boolean isClockChanged = false;
    public SystemClockVerifier() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("========= started thread =========");
        try (PrintStream log = new PrintStream(logFileName)) {
            log.println("# This file should have a record each minute to verify that system clock was not changed.");
            LocalDateTime previousTime = LocalDateTime.now();
            log.println("Start time: " + previousTime);
            while (!shouldStop) {
                Thread.sleep(checkInterval * 1000);
                LocalDateTime currentTime = LocalDateTime.now();
                log.println("Current time: " + currentTime);
                Duration duration = Duration.between(previousTime, currentTime);
                log.println("Difference: " + duration.get(SECONDS) + " seconds.");
                if (duration.isNegative() || duration.get(SECONDS) > checkInterval + 10) {
                    System.err.println("WARNING: clock was changed before " + currentTime
                            + " and after " + previousTime + ". See " +  logFileName);
                    isClockChanged = true;
                }
                previousTime = currentTime;
            }
            log.println("Finish time: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("WARNING: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    /**
     * Returns true if system clock was likely to be updated. The method might be executed before or after stop().
     */
    public boolean isClockChanged() {
        return isClockChanged;
    }

    public void stop() throws Exception {
        shouldStop = true;
    }
}