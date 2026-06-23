package karat.stock;

/**
 * We are developing a stock trading data management software that tracks the prices of different stocks over time
 * and provides useful statistics.
 * <p>
 * The program includes three classes: `Stock`, `PriceRecord`, and `StockCollection`.
 * <p>
 * Classes:
 * The `Stock` class represents data about a specific stock.
 * The `PriceRecord` class holds information about a single price record for a stock.
 * The `StockCollection` class manages a collection of price records for a particular stock and provides methods to
 * retrieve useful statistics about the stock's prices.
 * <p>
 * To begin with, we present you with two tasks:
 * 1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the
 * code.
 * <p>
 * 1-2) The test for StockCollection is not passing due to a bug in the code. Make the necessary changes to
 * StockCollection to fix the bug.
 * <p>
 * 2) We want to add a new function called "getBiggestChange" to the StockCollection class. This function calculates
 * and returns the largest change in stock price between any two consecutive days in the price records of a stock
 * along with the dates of the change in a list. For example, let's consider the following price records of a stock:
 * <p>
 * Price Records:
 * Price:  110         112         90          105
 * Date:   2023-06-29  2023-07-01  2023-06-25  2023-07-06
 * <p>
 * Stock price changes (sorted based on date):
 * Date:     2023-06-25  ->  2023-06-29  ->  2023-07-01 ->  2023-07-06
 * Price:        90      ->      110     ->     112     ->     105
 * Change:              +20              +2             -7
 * <p>
 * In this case, the biggest change in the stock price was +20, which occurred between 2023-06-25 and 2023-06-29.
 * In this case, the function should return [20, "2023-06-25", "2023-06-29"]
 * <p>
 * Two days are considered consecutive if there are no other days' data in between them in the price records based on
 * their dates.
 * <p>
 * To assist you in testing this new function, we have provided the testGetBiggestChange function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 * <p>
 * We are currently updating our system to include information about stock transactions. As part of this update,
 * we have introduced two new classes:
 * <p>
 * 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 * stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.
 * <p>
 * 2. The `Tradebook` class represents a list of transactions performed by a user.
 * <p>
 * You are provided with the code for the above classes. To complete the updates, we need to add the
 * `getTotal` function to the `Tradebook` class:
 * <p>
 * 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 * latest date. This functions helps the user to calculate the current value of their portfolio. This function
 * takes the `stockCollections` list as input which contains StockCollections for all stocks.
 * <p>
 * The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 * stock collection for each stock is guaranteed to have the price record for all transaction dates.
 * <p>
 * To assist you in testing these new functions, we have provided the `testTradebook` function.
 */
/**
 We are currently updating our system to include information about stock transactions. As part of this update,
 we have introduced two new classes:

 1. The `Transaction` class represents data about buy/sell transaction of a stock. It includes data about the
 stock, the type of transaction (buy/sell), the date of transaction and the quantity of the stocks.

 2. The `Tradebook` class represents a list of transactions performed by a user.

 You are provided with the code for the above classes. To complete the updates, we need to add the
 `getTotal` function to the `Tradebook` class:

 3-1) The `getTotal` function should be used to get the total price of all the stocks owned by the user at the
 latest date. This functions helps the user to calculate the current value of their portfolio. This function
 takes the `stockCollections` list as input which contains StockCollections for all stocks.

 The StockCollections list is guaranteed to contain all stocks which would be present in the Tradebook and the
 stock collection for each stock is guaranteed to have the price record for all transaction dates.

 To assist you in testing these new functions, we have provided the `testTradebook` function.
 */

import java.util.*;

class Transaction {
    /**
     * Data about a buy/sell transaction of a stock.
     */
    Stock stock; // Stock object representing the stock
    String transactionType; // String, "buy" or "sell"
    String date; // String, the date of the transaction in the format "YYYY-MM-DD"
    int quantity; // int, the quantity of stocks involved in the transaction

    Transaction(Stock stock, String transactionType, String date, int quantity) {
        this.stock = stock;
        this.transactionType = transactionType;
        this.date = date;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Transaction: " + stock + ", Type: " + transactionType + ", Date: " + date + ", Quantity: " + quantity;
    }
}

class Tradebook {
    /**
     * Represents a list of transactions and a list of StockCollection objects for all stocks.
     */
    ArrayList<Transaction> transactions = new ArrayList<>(); // list of Transaction objects, represents all transactions

    void addTransaction(Transaction transaction) {
        /** Adds a Transaction to the Tradebook. */
        transactions.add(transaction);
    }


    /**
     * Approach:
     * 1. Goal:
     *    Calculate total portfolio value based on latest stock prices.
     *
     * 2. Step 1: Calculate net quantity of each stock
     *    Use a HashMap:
     *      Key   → stock name
     *      Value → net quantity (buy = +, sell = -)
     *
     *    For each transaction:
     *      - If "buy"  → add quantity
     *      - If "sell" → subtract quantity
     *
     * 3. Step 2: Get latest price for each stock
     *    For each StockCollection:
     *      - Find price record with latest date (max by date)
     *
     * 4. Step 3: Compute total value
     *    For each stock:
     *      total += (latest price * net quantity)
     *
     * 5. Ignore stocks with 0 quantity (optional optimization).
     *
     * 6. Return final total portfolio value.
     *
     * Example:
     *   buy 10 @90, buy 5 @110, sell 3 → net = 12
     *   latest price = 105
     *   total = 12 * 105 = 1260
     *
     * Time Complexity:
     *   O(t + s * p)
     *   where:
     *      t = number of transactions
     *      s = number of stocks
     *      p = price records per stock
     *
     * Space Complexity: O(s) (quantity map)
     */

    public Object getTotal(ArrayList<StockCollection> testStockCollections) {
        // Write your code here
        Map<String, Integer> quantityMap = new HashMap<>();
        int total = 0;

        for (Transaction transaction : transactions) {
            int quantity = transaction.transactionType.equals("buy") ? transaction.quantity : -transaction.quantity;
            quantityMap.merge(transaction.stock.name, quantity, Integer::sum);
        }

        for (StockCollection stockCollection : testStockCollections) {
            int price = stockCollection.priceRecords.stream()
                    .max(Comparator.comparing(p -> p.date)).get().price;
            int quantity = quantityMap.get(stockCollection.stock.name);
            total += price * quantity;
        }
        return total;
    }
}

class Stock {
    /**
     * Data about a particular stock.
     */
    String symbol; // String, the symbol of the stock
    String name; // String, the name of the stock

    Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Stock stock = (Stock) other;
        return symbol.equals(stock.symbol) && name.equals(stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name);
    }
}

class PriceRecord {
    /**
     * Data and methods about a single price record of a stock.
     */
    Stock stock; // Stock object representing the stock
    int price; // int, the price of the stock
    String date; // String, the date of the price record is of the format "YYYY-MM-DD"

    PriceRecord(Stock stock, int price, String date) {
        this.stock = stock;
        this.price = price;
        this.date = date;
    }
}

class StockCollection {
    /**
     * Data for a collection of price records for a particular stock, and methods for
     * getting useful statistics about the stock's prices.
     */
    ArrayList<PriceRecord> priceRecords = new ArrayList<>(); // list of PriceRecord objects, the price records for this particular stock
    Stock stock; // Stock, the Stock this StockCollection is for

    StockCollection(Stock stock) {
        this.stock = stock;
    }

    int getNumPriceRecords() {
        /** Returns the number of PriceRecords in this StockCollection */
        if (priceRecords.isEmpty()) return -1;
        return priceRecords.size();
    }

    void addPriceRecord(PriceRecord priceRecord) {
        /** Adds a PriceRecord to this StockCollection. */
        if (!priceRecord.stock.equals(this.stock)) {
            throw new IllegalArgumentException("PriceRecord's Stock is not the same as the StockCollection's");
        }
        priceRecords.add(priceRecord);
    }

    int getMaxPrice() {
        /** Return the maximum price recorded in this StockCollection. */
        if (priceRecords.isEmpty()) return -1;
        return priceRecords.stream().mapToInt(record -> record.price).max().getAsInt();
    }

    int getMinPrice() {
        if (priceRecords.isEmpty()) return -1;
        /** Return the minimum price recorded in this StockCollection. */
        return priceRecords.stream().mapToInt(record -> record.price).min().getAsInt();
    }

    double getAvgPrice() {
        if (priceRecords.isEmpty()) return -1;
        /** Return the average price recorded in this StockCollection. */
        double total = priceRecords.stream().mapToInt(record -> record.price).sum();
        return total / priceRecords.size();
    }


    /**
     * Approach:
     * 1. If there are less than 2 price records → no comparison possible → return null.
     *
     * 2. We need to find the largest price change between two consecutive days:
     *    - "Consecutive" means after sorting records by date.
     *
     * 3. Steps:
     *    a. Create a copy of priceRecords and sort it by date (ascending).
     *    b. Traverse the sorted list from index 1 to n-1.
     *    c. For each pair:
     *          prev = records[i-1]
     *          curr = records[i]
     *          change = curr.price - prev.price
     *
     *    d. Track:
     *          - max absolute difference (largest movement)
     *          - actual change (can be + or -)
     *          - corresponding start date and end date
     *
     * 4. Return result as:
     *      [change, startDate, endDate]
     *
     * Example:
     *   90 -> 110  = +20 ✅ largest change
     *
     * Time Complexity: O(n log n) (sorting)
     * Space Complexity: O(n) (copy list)
     */

    public Object[] getBiggestChange() {
        // Write your code here

        if (priceRecords.size() < 2) return null;

        List<PriceRecord> records = new ArrayList<>(priceRecords);
        records.sort(Comparator.comparing(r -> r.date));

        int maxDiff = Integer.MIN_VALUE;
        int maxChange = 0;
        String startDate = "";
        String endDate = "";

        for (int i = 1; i < priceRecords.size(); i++) {
            PriceRecord prev = records.get(i - 1);
            PriceRecord cur = records.get(i);

            int change = cur.price - prev.price;

            if (Math.abs(change) > maxDiff) {
                maxDiff = Math.abs(change);
                maxChange = change;
                startDate = prev.date;
                endDate = cur.date;
            }
        }
        return new Object[]{maxChange, startDate, endDate};
    }
}

public class StockUseCase {

    public static void main(String[] args) {
        testPriceRecord();
        testStockCollection();
        testGetBiggestChange();
        testTradebook();
    }

    // ✅ Custom Assertions
    static void assertEquals(Object expected, Object actual, String testName) {
        if (Objects.equals(expected, actual)) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName +
                    " | Expected: " + expected + " but got: " + actual);
        }
    }

    static void assertDouble(double expected, double actual, double delta, String testName) {
        if (Math.abs(expected - actual) <= delta) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName +
                    " | Expected: " + expected + " but got: " + actual);
        }
    }

    static void assertNull(Object obj, String testName) {
        if (obj == null) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName + " | Expected NULL");
        }
    }

    static void assertArray(Object[] expected, Object[] actual, String testName) {
        if (Arrays.equals(expected, actual)) {
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName +
                    " | Expected: " + Arrays.toString(expected) +
                    " but got: " + Arrays.toString(actual));
        }
    }

    // -----------------------------------------------------

    public static void testPriceRecord() {
        System.out.println("Running testPriceRecord");

        Stock testStock = new Stock("AAPL", "Apple Inc.");
        PriceRecord testPriceRecord = new PriceRecord(testStock, 100, "2023-07-01");

        assertEquals(testStock, testPriceRecord.stock, "Stock check");
        assertEquals(100, testPriceRecord.price, "Price check");
        assertEquals("2023-07-01", testPriceRecord.date, "Date check");
    }

    private static StockCollection makeStockCollection(Stock stock, Object[][] priceData) {
        StockCollection stockCollection = new StockCollection(stock);
        for (Object[] data : priceData) {
            PriceRecord pr = new PriceRecord(stock, (int) data[0], (String) data[1]);
            stockCollection.addPriceRecord(pr);
        }
        return stockCollection;
    }

    public static void testStockCollection() {
        System.out.println("Running testStockCollection");

        Stock stock = new Stock("AAPL", "Apple Inc.");
        StockCollection sc = new StockCollection(stock);

        assertEquals(0, sc.getNumPriceRecords(), "Empty size");
        assertEquals(-1, sc.getMaxPrice(), "Empty max");
        assertEquals(-1, sc.getMinPrice(), "Empty min");
        assertDouble(-1.0, sc.getAvgPrice(), 0.001, "Empty avg");

        Object[][] priceData = {
                {110, "2023-06-29"},
                {112, "2023-07-01"},
                {90, "2023-06-28"},
                {105, "2023-07-06"}
        };

        sc = makeStockCollection(stock, priceData);

        assertEquals(4, sc.getNumPriceRecords(), "Size check");
        assertEquals(112, sc.getMaxPrice(), "Max price");
        assertEquals(90, sc.getMinPrice(), "Min price");
        assertDouble(104.25, sc.getAvgPrice(), 0.1, "Avg price");
    }

    public static void testGetBiggestChange() {
        System.out.println("Running testGetBiggestChange");

        Stock stock = new Stock("AAPL", "Apple Inc.");
        StockCollection sc = new StockCollection(stock);

        assertNull(sc.getBiggestChange(), "Empty biggest change");

        Object[][] priceData = {
                {110, "2023-06-29"},
                {112, "2023-07-01"},
                {90, "2023-06-25"},
                {105, "2023-07-06"}
        };

        sc = makeStockCollection(stock, priceData);

        assertArray(
                new Object[]{20, "2023-06-25", "2023-06-29"},
                sc.getBiggestChange(),
                "Biggest change 1"
        );

        Object[][] priceData2 = {
                {200, "2000-01-04"},
                {210, "1999-12-30"},
                {190, "2000-01-03"},
                {180, "2000-01-01"}
        };

        sc = makeStockCollection(stock, priceData2);

        assertArray(
                new Object[]{-30, "1999-12-30", "2000-01-01"},
                sc.getBiggestChange(),
                "Biggest change 2"
        );
    }

    public static void testTradebook() {
        System.out.println("Running testTradebook");

        Tradebook tradebook = new Tradebook();

        Stock s1 = new Stock("AAPL", "Apple Inc.");
        Object[][] data1 = {
                {110, "2023-06-29"},
                {112, "2023-07-01"},
                {90, "2023-06-25"},
                {105, "2023-07-06"}
        };

        StockCollection sc1 = makeStockCollection(s1, data1);

        ArrayList<StockCollection> list = new ArrayList<>();
        list.add(sc1);

        tradebook.addTransaction(new Transaction(s1, "buy", "2023-06-25", 10));
        assertEquals(1050, tradebook.getTotal(list), "Total 1");

        tradebook.addTransaction(new Transaction(s1, "buy", "2023-06-29", 5));
        tradebook.addTransaction(new Transaction(s1, "sell", "2023-07-01", 3));
        assertEquals(1260, tradebook.getTotal(list), "Total 2");

        Stock s2 = new Stock("GOOG", "Alphabet Inc.");
        Stock s3 = new Stock("MSFT", "Microsoft Corporation");
        Stock s4 = new Stock("AMZN", "Amazon.com Inc.");

        Object[][] d2 = {
                {1500, "2023-06-29"},
                {1550, "2023-07-01"},
                {1475, "2023-06-25"},
                {1520, "2023-07-06"}
        };
        Object[][] d3 = {
                {250, "2023-06-29"},
                {255, "2023-07-01"},
                {245, "2023-06-25"},
                {260, "2023-07-06"}
        };
        Object[][] d4 = {
                {3500, "2023-06-29"},
                {3600, "2023-07-01"},
                {3450, "2023-06-25"},
                {3550, "2023-07-06"}
        };

        list.add(makeStockCollection(s2, d2));
        list.add(makeStockCollection(s3, d3));
        list.add(makeStockCollection(s4, d4));

        tradebook.addTransaction(new Transaction(s2, "buy", "2023-06-25", 15));
        tradebook.addTransaction(new Transaction(s2, "sell", "2023-06-29", 10));
        tradebook.addTransaction(new Transaction(s2, "sell", "2023-07-01", 1));

        tradebook.addTransaction(new Transaction(s3, "sell", "2023-07-01", 5));
        tradebook.addTransaction(new Transaction(s3, "buy", "2023-06-29", 20));
        tradebook.addTransaction(new Transaction(s3, "buy", "2023-06-25", 10));

        tradebook.addTransaction(new Transaction(s4, "buy", "2023-07-01", 5));
        tradebook.addTransaction(new Transaction(s4, "buy", "2023-06-29", 5));
        tradebook.addTransaction(new Transaction(s4, "buy", "2023-06-25", 1));

        assertEquals(52890, tradebook.getTotal(list), "Final Total");
    }
}
