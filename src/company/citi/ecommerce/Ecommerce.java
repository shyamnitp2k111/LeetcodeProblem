package company.citi.ecommerce;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * We are developing an e-commerce analytics system that tracks products, customer purchases, and sales data.

 * The program includes three classes: `Product`, `Purchase`, and `SalesAnalyzer`.

 * Classes:
 * The `Product` class represents a product available for sale.
 * The `Purchase` class holds information about a customer's purchase transaction.
 * The `SalesAnalyzer` class manages all purchases and provides analytical insights.

 * To begin with, we present you with two tasks:
 * 1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
 * 1-2) The test for SalesAnalyzer is not passing due to a bug in the code. Make the necessary changes to SalesAnalyzer to fix the bug.

 * 2) We want to add a new function called "getTopSellingProduct" to the SalesAnalyzer class.
 * This function analyzes all purchases and returns the product that has been purchased the most times
 * (by quantity, not by number of transactions) along with the total quantity sold.

 * For example, if purchases include:
 * - Product A: 5 units in transaction 1, 3 units in transaction 2 = 8 total
 * - Product B: 10 units in transaction 3 = 10 total
 * - Product C: 2 units in transaction 4, 2 units in transaction 5 = 4 total

 * The function should return [Product B object, 10].

 * To assist you in testing this new function, we have provided the testGetTopSellingProduct function.
 */


class Product {
    /**
     * Data about a product.
     */
    String productId;
    String name;
    String category;
    double price;

    Product(String productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Product product = (Product) other;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}

class Purchase {
    /**
     * Data about a customer purchase.
     */
    Product product;
    int quantity;
    String customerId;
    String purchaseDate;    // Format: "YYYY-MM-DD"

    Purchase(Product product, int quantity, String customerId, String purchaseDate) {
        this.product = product;
        this.quantity = quantity;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
    }

    double getTotalAmount() {
        return product.price * quantity;
    }
}

class SalesAnalyzer {
    /**
     * Manages purchase data and provides analytical insights.
     */
    ArrayList<Purchase> purchases = new ArrayList<>();

    SalesAnalyzer() {
    }

    void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    int getTotalPurchases() {
        return purchases.size();
    }

    double getTotalRevenue() {
        return purchases.stream().mapToDouble(p -> p.getTotalAmount()).sum();
    }

    int getTotalItemsSold() {
        return purchases.stream().mapToInt(p -> p.quantity).sum();
    }

    double getAveragePurchaseValue() {
        /**
         * Returns the average value per purchase transaction.
         * BUG: This method has a bug - fix it!
         */
        if (getTotalPurchases() == 0) return 0.0;
        double value = getTotalRevenue() / getTotalPurchases();
        if(Double.isNaN(value)) {
            return 0.0;
        }

        return value;
    }

    int getUniqueCustomers() {
        /** Returns the count of unique customers who made purchases. */
        return (int) purchases.stream()
                .map(p -> p.customerId)
                .distinct()
                .count();
    }

    /**
     * Approach:
     * 1. Convert purchases list into a stream.
     * 2. Group purchases by Product and sum their quantities using Collectors.summingInt().
     * 3. This gives a Map<Product, Integer> where value = total quantity sold.
     * 4. Convert map entries to stream and find the entry with maximum quantity.
     * 5. Return result as Object[] → [Product, totalQuantity].
     * 6. Return null if no purchases exist.
     */
    public Object[] getTopSellingProduct() {

        if(purchases.isEmpty()) {
            return null;
        }

        return purchases.stream()
                .collect(Collectors.groupingBy(t -> t.product,
                        Collectors.summingInt(t -> t.quantity)))
                .entrySet().stream().max((e1,e2) ->  e1.getValue() - e2.getValue())
                .map(e1 -> new Object[]{e1.getKey(), e1.getValue()})
                .orElse(null);
    }
}

public class Ecommerce {

    public static void main(String[] args) {
        testPurchase();
        testSalesAnalyzer();
        testGetTopSellingProduct();
    }

    // ✅ helper methods
    static void checkEquals(Object expected, Object actual, String testName) {
        if (Objects.equals(expected, actual)) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName +
                    " | Expected: " + expected + " but got: " + actual);
        }
    }

    static void checkDouble(double expected, double actual, double delta, String testName) {
        if (Math.abs(expected - actual) <= delta) {
            System.out.println("✅ PASS: " + testName);
            System.out.println("✅ PASS: " + testName +
                    " | Expected: " + expected + " but got: " + actual);
        } else {
            System.out.println("❌ FAIL: " + testName +
                    " | Expected: " + expected + " but got: " + actual);
        }
    }

    static void checkNull(Object obj, String testName) {
        if (obj == null) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName + " | Expected NULL");
        }
    }

    // ---------------------------------------------------

    public static void testPurchase() {
        System.out.println("Running testPurchase");

        Product product = new Product("P001", "Laptop", "Electronics", 999.99);
        Purchase purchase = new Purchase(product, 2, "C001", "2024-02-01");

        checkEquals(2, purchase.quantity, "Quantity");
        checkDouble(1999.98, purchase.getTotalAmount(), 0.01, "Total Amount");
    }

    public static void testSalesAnalyzer() {
        System.out.println("Running testSalesAnalyzer");

        SalesAnalyzer analyzer = new SalesAnalyzer();

        checkEquals(0, analyzer.getTotalPurchases(), "Initial purchases");
        checkDouble(0.0, analyzer.getTotalRevenue(), 0.01, "Initial revenue");
        checkDouble(0.0, analyzer.getAveragePurchaseValue(), 0.01, "Average value");

        Product p1 = new Product("P001", "Laptop", "Electronics", 1000.0);
        Product p2 = new Product("P002", "Mouse", "Accessories", 25.0);
        Product p3 = new Product("P003", "Keyboard", "Accessories", 75.0);

        analyzer.addPurchase(new Purchase(p1, 1, "C001", "2024-02-01"));
        analyzer.addPurchase(new Purchase(p2, 2, "C002", "2024-02-02"));
        analyzer.addPurchase(new Purchase(p3, 1, "C001", "2024-02-03"));

        checkEquals(3, analyzer.getTotalPurchases(), "Total purchases");
        checkDouble(1125.0, analyzer.getTotalRevenue(), 0.01, "Total revenue");
        checkEquals(4, analyzer.getTotalItemsSold(), "Items sold");
        checkDouble(375.0, analyzer.getAveragePurchaseValue(), 0.01, "Average value");
        checkEquals(2, analyzer.getUniqueCustomers(), "Unique customers");
    }

    public static void testGetTopSellingProduct() {
        System.out.println("Running testGetTopSellingProduct");

        SalesAnalyzer analyzer = new SalesAnalyzer();

        checkNull(analyzer.getTopSellingProduct(), "Empty top product");

        Product p1 = new Product("P001", "Product A", "Category1", 10.0);
        Product p2 = new Product("P002", "Product B", "Category2", 20.0);
        Product p3 = new Product("P003", "Product C", "Category1", 15.0);

        analyzer.addPurchase(new Purchase(p1, 5, "C001", "2024-02-01"));
        analyzer.addPurchase(new Purchase(p1, 3, "C002", "2024-02-02"));
        analyzer.addPurchase(new Purchase(p2, 10, "C003", "2024-02-03"));
        analyzer.addPurchase(new Purchase(p3, 2, "C001", "2024-02-04"));
        analyzer.addPurchase(new Purchase(p3, 2, "C002", "2024-02-05"));

        Object[] result = analyzer.getTopSellingProduct();

        checkEquals(p2, result[0], "Top Product");
        checkEquals(10, result[1], "Top Quantity");
    }
}
