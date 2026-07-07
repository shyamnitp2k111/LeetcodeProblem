package companyinterview.citi.deliveryTrackingSystem;

/**
We are developing a package delivery tracking system that monitors shipments, delivery routes, and performance
 metrics.

The program includes three classes: `Package`, `DeliveryRecord`, and `DeliveryTracker`.

Classes:
* The `Package` class represents a package being shipped.
* The `DeliveryRecord` class holds information about a delivery.
* The `DeliveryTracker` class manages all deliveries and provides performance analytics.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for DeliveryTracker is not passing due to a bug in the code. Make the necessary changes to
     DeliveryTracker to fix the bug.


2) We want to add a new function called "getOnTimeDeliveryRate" to the DeliveryTracker class.
This function calculates what percentage of deliveries were completed on time or early.

A delivery is considered on-time if the actualDeliveryDate is the same as or before the
expectedDeliveryDate.

For example, if there are 10 deliveries:
- 8 were delivered on time or early
- 2 were delivered late

The function should return 80.0 (representing 80%).

Only consider deliveries that have been completed (actualDeliveryDate is not null).

To assist you in testing this new function, we have provided the testGetOnTimeDeliveryRate function.
*/

import org.junit.Assert;

import java.util.*;


class Package {
    /** Data about a package. */
    String trackingNumber;
    String senderAddress;
    String recipientAddress;
    double weight;          // in kilograms
    String packageType;     // "Standard", "Express", "Overnight"

    Package(String trackingNumber, String senderAddress, String recipientAddress,
            double weight, String packageType) {
        this.trackingNumber = trackingNumber;
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
        this.weight = weight;
        this.packageType = packageType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Package pkg = (Package) other;
        return trackingNumber.equals(pkg.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}

class DeliveryRecord {
    /** Data about a delivery. */
    Package packageObj;
    String driverName;
    String shipDate;                // Format: "YYYY-MM-DD"
    String expectedDeliveryDate;    // Format: "YYYY-MM-DD"
    String actualDeliveryDate;      // Format: "YYYY-MM-DD", null if not delivered yet
    String status;                  // "In Transit", "Delivered", "Failed"

    DeliveryRecord(Package packageObj, String driverName, String shipDate,
                   String expectedDeliveryDate, String actualDeliveryDate, String status) {
        this.packageObj = packageObj;
        this.driverName = driverName;
        this.shipDate = shipDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.status = status;
    }

    boolean isOnTime() {
        /** Check if delivery was on time. Returns false if not yet delivered. */
        if (actualDeliveryDate == null) return false;
        // Simple date comparison (YYYY-MM-DD format allows string comparison)
        return actualDeliveryDate.compareTo(expectedDeliveryDate) <= 0;
    }
}

class DeliveryTracker {
    /**
     * Manages delivery records and provides performance analytics.
     */
    ArrayList<DeliveryRecord> deliveries = new ArrayList<>();
    String companyName;

    DeliveryTracker(String companyName) {
        this.companyName = companyName;
    }

    void addDelivery(DeliveryRecord delivery) {
        deliveries.add(delivery);
    }

    int getTotalDeliveries() {
        return deliveries.size();
    }

    int getDeliveriesByStatus(String status) {
        /** Returns count of deliveries with a specific status. */
        return (int) deliveries.stream()
                .filter(d -> d.status.equals(status))
                .count();
    }

    int getDeliveriesByDriver(String driverName) {
        /** Returns count of deliveries handled by a specific driver. */
        return (int) deliveries.stream()
                .filter(d -> d.driverName.equals(driverName))
                .count();
    }

    double getAveragePackageWeight() {
        /**
         * Returns the average package weight.
         * BUG: This method has a bug - fix it!
         */
        return deliveries.stream().mapToDouble(value -> value.packageObj.weight).average().orElse(0.0);
    }

    public double getOnTimeDeliveryRate() {
        // Write your code here to solve this problem
        if (deliveries.isEmpty()) return 0.0;

        long completed = deliveries.stream().filter(deliveryRecord -> deliveryRecord.actualDeliveryDate != null)
                .count();

        if(completed == 0) return 0.0;

        long onTime = deliveries.stream().filter(deliveryRecord -> deliveryRecord.actualDeliveryDate != null)
                .filter(deliveryRecord -> deliveryRecord.isOnTime()).count();

        return (onTime * 100.0) / completed;
    }
}

public class DeliveryTracking {
    public static void main(String[] args) {
        testDeliveryRecord();
        testDeliveryTracker();
        testGetOnTimeDeliveryRate();
    }

    public static void testDeliveryRecord() {
        System.out.println("Running testDeliveryRecord");
        Package pkg = new Package("TRK001", "123 Main St", "456 Oak Ave", 2.5, "Standard");
        DeliveryRecord record = new DeliveryRecord(pkg, "John Driver", "2024-02-01",
                "2024-02-05", "2024-02-04", "Delivered");

        Assert.assertTrue(record.isOnTime());
        Assert.assertEquals("Delivered", record.status);
    }

    public static void testDeliveryTracker() {
        System.out.println("Running testDeliveryTracker");
        DeliveryTracker tracker = new DeliveryTracker("Fast Delivery Co.");

        Assert.assertEquals(0, tracker.getTotalDeliveries());

        Package p1 = new Package("TRK001", "Addr1", "Addr2", 2.5, "Standard");
        Package p2 = new Package("TRK002", "Addr3", "Addr4", 1.0, "Express");
        Package p3 = new Package("TRK003", "Addr5", "Addr6", 3.5, "Overnight");

        DeliveryRecord d1 = new DeliveryRecord(p1, "Driver A", "2024-02-01", "2024-02-05", "2024-02-04", "Delivered");
        DeliveryRecord d2 = new DeliveryRecord(p2, "Driver B", "2024-02-02", "2024-02-03", "2024-02-03", "Delivered");
        DeliveryRecord d3 = new DeliveryRecord(p3, "Driver A", "2024-02-03", "2024-02-05", null, "In Transit");

        tracker.addDelivery(d1);
        tracker.addDelivery(d2);
        tracker.addDelivery(d3);

        Assert.assertEquals(3, tracker.getTotalDeliveries());
        Assert.assertEquals(2, tracker.getDeliveriesByStatus("Delivered"));
        Assert.assertEquals(2, tracker.getDeliveriesByDriver("Driver A"));
        Assert.assertEquals(2.33, tracker.getAveragePackageWeight(), 0.01);
    }

    public static void testGetOnTimeDeliveryRate() {
        System.out.println("Running testGetOnTimeDeliveryRate");
        DeliveryTracker tracker = new DeliveryTracker("Fast Delivery Co.");

        Package p1 = new Package("TRK001", "Addr1", "Addr2", 2.5, "Standard");
        Package p2 = new Package("TRK002", "Addr3", "Addr4", 1.0, "Express");
        Package p3 = new Package("TRK003", "Addr5", "Addr6", 3.5, "Overnight");
        Package p4 = new Package("TRK004", "Addr7", "Addr8", 2.0, "Standard");
        Package p5 = new Package("TRK005", "Addr9", "Addr10", 1.5, "Express");

        // 8 on-time deliveries out of 10 completed
        DeliveryRecord d1 = new DeliveryRecord(p1, "Driver A", "2024-02-01", "2024-02-05", "2024-02-04", "Delivered");
        DeliveryRecord d2 = new DeliveryRecord(p2, "Driver B", "2024-02-01", "2024-02-03", "2024-02-03", "Delivered");
        DeliveryRecord d3 = new DeliveryRecord(p3, "Driver A", "2024-02-01", "2024-02-05", "2024-02-06", "Delivered");
        DeliveryRecord d4 = new DeliveryRecord(p4, "Driver B", "2024-02-01", "2024-02-04", "2024-02-03", "Delivered");
        DeliveryRecord d5 = new DeliveryRecord(p5, "Driver A", "2024-02-01", "2024-02-05", null, "In Transit");

        tracker.addDelivery(d1);
        tracker.addDelivery(d2);
        tracker.addDelivery(d3);
        tracker.addDelivery(d4);
        tracker.addDelivery(d5);

        Assert.assertEquals(75.0, tracker.getOnTimeDeliveryRate(), 0.01);  // 3 out of 4 completed = 75%
    }
}

