package companyinterview;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowRateLimiter {

    private int requestLimit;
    private Duration timeWindow;
    private Map<String, Record> map = new HashMap<>();

    public SlidingWindowRateLimiter(int requestLimit, Duration timeWindow) {
        this.requestLimit = requestLimit;
        this.timeWindow = timeWindow;
    }

    public boolean isAllowed(String customerId) {
        Instant now = Instant.now();

        Record record = map.get(customerId);
        if (record == null) {
            // First request from this customer
            map.put(customerId, new Record(now, 1));
            return true;
        }

        // Check if current window has expired
        if (now.isAfter(record.getWindowStart().plus(timeWindow))) {
            // Reset window
            record.setWindowStart(now);
            record.setNoOfRequest(1);
            return true;
        }

        // Window active, check request limit
        if (record.getNoOfRequest() < requestLimit) {
            record.setNoOfRequest(record.getNoOfRequest() + 1);
            return true;
        }

        // Limit exceeded
        return false;
    }

    // Record class for storing state per customer
    static class Record {
        private Instant windowStart;
        private int noOfRequest;

        public Record(Instant windowStart, int noOfRequest) {
            this.windowStart = windowStart;
            this.noOfRequest = noOfRequest;
        }

        public Instant getWindowStart() {
            return windowStart;
        }

        public void setWindowStart(Instant windowStart) {
            this.windowStart = windowStart;
        }

        public int getNoOfRequest() {
            return noOfRequest;
        }

        public void setNoOfRequest(int noOfRequest) {
            this.noOfRequest = noOfRequest;
        }
    }

    // Example usage
    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(3, Duration.ofSeconds(5));
        String customer = "user1";

        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.isAllowed(customer);
            System.out.println("Request " + i + " allowed: " + allowed);
            Thread.sleep(1000); // simulate 1 second between requests
        }
    }
}