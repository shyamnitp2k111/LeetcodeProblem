package companyinterview.citi.stock1;

import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Enum representing the sector/type classification of a stock.
 * Used to categorize stocks by industry for portfolio diversification and analysis.
 */
enum StockType {
    TECHNOLOGY,     // Tech companies (software, hardware, IT services)
    FINANCE,        // Financial institutions (banks, insurance, investments)
    HEALTHCARE,     // Healthcare and pharmaceutical companies
    ENERGY,         // Oil, gas, renewable energy companies
    CONSUMER        // Consumer goods and retail companies
}

/**
 * Represents a tradable stock with sector classification.
 * Each stock has a unique symbol, company name, and industry sector.
 */
class Stock {
    public String symbol;       // Stock ticker symbol (e.g., "AAPL")
    public String name;         // Company name (e.g., "Apple Inc.")
    public StockType stockType; // Sector classification

    /**
     * Constructor for creating a Stock with sector classification.
     *
     * @param symbol Stock ticker symbol
     * @param name Company name
     * @param type Sector classification
     */
    public Stock(String symbol, String name, StockType type) {
        this.symbol = symbol;
        this.name = name;
        this.stockType = type;
    }

    /**
     * Legacy constructor for backward compatibility (defaults to TECHNOLOGY).
     *
     * @param symbol Stock ticker symbol
     * @param name Company name
     */
    public Stock(String symbol, String name) {
        this(symbol, name, StockType.TECHNOLOGY);
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
        return java.util.Objects.hash(symbol, name);
    }

    @Override
    public String toString() {
        return symbol + " (" + name + ")";
    }
}

/**
 * Represents a single price record for a stock at a specific date.
 * Contains the stock reference, price value, and date information.
 */
class PriceRecord {
    public Stock stock;     // Stock object representing the stock
    public int price;       // Price of the stock in cents (to avoid floating point issues)
    public String date;     // Date in format "YYYY-MM-DD"

    /**
     * Creates a price record for a stock.
     *
     * @param stock The stock this price record belongs to
     * @param price The price value in cents
     * @param date The date in "YYYY-MM-DD" format
     */
    public PriceRecord(Stock stock, int price, String date) {
        this.stock = stock;
        this.price = price;
        this.date = date;
    }
}

/**
 * Manages a collection of price records for a specific stock.
 * Provides statistical analysis methods for price trends and volatility.
 */
class StockCollection {
    public ArrayList<PriceRecord> priceRecords = new ArrayList<>();
    public Stock stock;

    /**
     * Creates a new StockCollection for a specific stock.
     *
     * @param stock The stock this collection tracks
     */
    public StockCollection(Stock stock) {
        this.stock = stock;
    }

    /**
     * Returns the number of price records in this collection.
     *
     * @return Count of price records
     */
    public int getNumPriceRecords() {
        return priceRecords.size();
    }

    /**
     * Adds a price record to this collection after validation.
     *
     * @param priceRecord The price record to add
     * @throws IllegalArgumentException if stock doesn't match
     */
    public void addPriceRecord(PriceRecord priceRecord) {
        if (!priceRecord.stock.equals(this.stock)) {
            throw new IllegalArgumentException("PriceRecord's Stock is not the same as the StockCollection's");
        }
        priceRecords.add(priceRecord);
    }

    /**
     * Returns the maximum price recorded in this StockCollection.
     * There's issue in this method - Fix it
     *
     * @return Maximum price, or -1 if no records exist
     */
    public int getMaxPrice() {
        // BUGGY CODE
        if (priceRecords.isEmpty()) {
            return -1;
        }

        return priceRecords.stream()
                .mapToInt(record -> record.price)
                .max()
                .getAsInt();
    }

    /**
     * ISSUE #1: Returns the minimum price recorded in this StockCollection.
     *
     *
     * @return Minimum price, or -1 if no records exist
     */
    public int getMinPrice() {
        if(priceRecords.isEmpty()) return -1;
        // BUGGY CODE - Stream reuse issue

        return priceRecords.stream().mapToInt(record -> record.price).min().orElse(-1);

        //IntStream prices = priceRecords.stream()
        //                              .mapToInt(record -> record.price);

        // First terminal operation - consumes the stream
        //if (prices.count() == 0) {
        //    return -1;
        //}

        // BUG: Trying to reuse the already-consumed stream
        //return prices.min().orElse(-1);
    }

    /**
     * Returns the average price recorded in this StockCollection.
     *
     * @return Average price, or -1.0 if no records exist
     */
    public double getAvgPrice() {
        if (priceRecords.isEmpty()) {
            return -1.0;
        }
        double total = priceRecords.stream()
                .mapToInt(record -> record.price)
                .sum();
        return total / priceRecords.size();
    }

    /**
     * Calculates the biggest price change between consecutive days.
     * Returns array: [change amount, start date, end date]
     *
     * @return Array with change details, or null if insufficient data
     */
    public Object[] getBiggestChange() {
        if (priceRecords.size() < 2) {
            return null;
        }

        // Sort price records by date
        ArrayList<PriceRecord> sortedRecords = new ArrayList<>(priceRecords);
        sortedRecords.sort(Comparator.comparing(record -> record.date));

        int maxChange = Integer.MIN_VALUE;
        String startDate = null;
        String endDate = null;

        for (int i = 0; i < sortedRecords.size() - 1; i++) {
            PriceRecord current = sortedRecords.get(i);
            PriceRecord next = sortedRecords.get(i + 1);
            int change = next.price - current.price;

            if (Math.abs(change) > Math.abs(maxChange)) {
                maxChange = change;
                startDate = current.date;
                endDate = next.date;
            }
        }

        return new Object[] { maxChange, startDate, endDate };
    }

    /**
     * TASK #3: Calculate price volatility (standard deviation).
     *
     * Volatility measures how much a stock's price fluctuates.
     * Higher values indicate more price swings (higher risk/reward).
     *
     * Algorithm:
     * 1. Calculate mean price
     * 2. For each price: compute (price - mean)²
     * 3. Sum all squared differences
     * 4. Divide by count (population standard deviation)
     * 5. Take square root
     *
     * Requirements:
     * - Return 0.0 if less than 2 price records
     * - Use all price records
     * - Return double value
     *
     * Example:
     *   Prices: [90, 110, 112, 105]
     *   Mean: 104.25
     *   Squared differences: [(90-104.25)², (110-104.25)², (112-104.25)², (105-104.25)²]
     *   Variance: sum / 4
     *   Volatility: sqrt(variance)
     *
     * @return Standard deviation of prices, or 0.0 if insufficient data
     */
    public double getVolatility() {
        // TODO: Implement this method
        if(priceRecords.size() < 2) return 0.0;

        double mean = priceRecords.stream().mapToInt(record -> record.price).average().orElse(0.0);

        double varience = priceRecords.stream()
                .mapToDouble(record -> Math.pow(record.price - mean, 2)).sum() / priceRecords.size();

        return Math.sqrt(varience);
    }
}

/**
 * Represents a buy or sell transaction for a stock.
 * Tracks the stock, transaction type, date, and quantity.
 */
class Transaction {
    public Stock stock;             // Stock being traded
    public String transactionType;  // "buy" or "sell"
    public String date;             // Transaction date "YYYY-MM-DD"
    public int quantity;            // Number of shares

    /**
     * Creates a new transaction record.
     *
     * @param stock Stock being traded
     * @param transactionType "buy" or "sell"
     * @param date Date of transaction
     * @param quantity Number of shares
     */
    public Transaction(Stock stock, String transactionType, String date, int quantity) {
        this.stock = stock;
        this.transactionType = transactionType;
        this.date = date;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Transaction: " + stock + ", Type: " + transactionType +
                ", Date: " + date + ", Quantity: " + quantity;
    }
}

/**
 * Manages a portfolio of stock transactions.
 * Tracks buy/sell operations and calculates portfolio value.
 */
class Tradebook {
    public ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Adds a transaction to the tradebook.
     *
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * ISSUE: Calculates total portfolio value at the latest date.
     *
     * For each stock:
     * 1. Calculate net position (buys - sells)
     * 2. Get latest price from StockCollections
     * 3. Multiply position × latest price
     * 4. Sum across all stocks
     *
     * PROBLEM: Edge cases are not properly handled.
     * What if a stock in transactions doesn't exist in stockCollections?
     * What if StockCollection for a stock is null or has no price records?
     *
     * @param stockCollections List of StockCollections with price data
     * @return Total portfolio value, or -1 if calculation fails
     */
    public int getTotal(ArrayList<StockCollection> stockCollections) {
        // BUGGY CODE - Edge case not handled


        // Calculate net position for each stock
        HashMap<Stock, Integer> positions = new HashMap<>();
        for (Transaction transaction : transactions) {
            int currentPosition = positions.getOrDefault(transaction.stock, 0);
            if (transaction.transactionType.equals("buy")) {
                positions.put(transaction.stock, currentPosition + transaction.quantity);
            } else {
                positions.put(transaction.stock, currentPosition - transaction.quantity);
            }
        }

        // Calculate total value
        int total = 0;
        for (Stock stock : positions.keySet()) {
            int position = positions.get(stock);

            // Find stock collection for this stock
            StockCollection stockCollection = null;
            for (StockCollection sc : stockCollections) {
                if (sc.stock.equals(stock)) {
                    stockCollection = sc;
                    break;
                }
            }

            // Issue: What if stockCollection is null? Or has no records?
            // Get latest price (assuming records are added chronologically)
            int latestIndex = stockCollection.priceRecords.size() - 1;
            int latestPrice = stockCollection.priceRecords.get(latestIndex).price;

            total += position * latestPrice;
        }

        return total;
    }
}

/**
 * Central registry for managing stocks across different sectors.
 * Provides filtering and lookup capabilities by stock type.
 */
class StockRegistry {
    private ArrayList<Stock> stocks;

    /**
     * Creates a new StockRegistry with empty stock list.
     */
    public StockRegistry() {
        stocks = new ArrayList<>();
    }

    /**
     * Adds a stock to the registry.
     *
     * @param stock The stock to add
     */
    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    /**
     * TASK #4: Retrieves all stocks matching a specific sector/type.
     *
     * This filtering allows investors to:
     * - Diversify portfolios across sectors
     * - Analyze sector-specific trends
     * - Compare stocks within same industry
     *
     * Requirements:
     * - Use streams for filtering
     * - Return empty list if no matches
     * - Return empty list if stockType is null
     * - Preserve original order
     * - Do not modify original stocks list
     *
     * Example:
     *   registry.addStock(new Stock("AAPL", "Apple Inc.", TECHNOLOGY));
     *   registry.addStock(new Stock("JPM", "JPMorgan Chase", FINANCE));
     *
     *   List<Stock> techStocks = registry.getStocksByType(TECHNOLOGY);
     *   // Returns: [AAPL]
     *
     * @param stockType The StockType to filter by
     * @return List of stocks matching the type (empty if none match)
     */
    public ArrayList<Stock> getStocksByType(StockType stockType) {
        // TODO: Implement this method
        return stocks.stream().filter(stock -> stockType.equals(stock.stockType))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns all stocks in the registry.
     *
     * @return List of all stocks (defensive copy)
     */
    public ArrayList<Stock> getAllStocks() {
        return new ArrayList<>(stocks);
    }
}

/**
 * Main test class demonstrating the stock trading system.
 * Contains comprehensive test cases for all functionality.
 */
public class Solution {

    public static void main(String[] args) {
        testPriceRecord();
        testStockCollection();
        testGetBiggestChange();
        testTradebook();
        testStockTypes();
        testVolatility();
        System.out.println("\nAll tests passed successfully!");
    }

    /**
     * Tests basic PriceRecord functionality.
     */
    public static void testPriceRecord() {
        System.out.println("Running testPriceRecord");
        Stock testStock = new Stock("AAPL", "Apple Inc.");
        PriceRecord testPriceRecord = new PriceRecord(testStock, 100, "2023-07-01");

        Assert.assertEquals(testPriceRecord.stock, testStock);
        Assert.assertEquals(testPriceRecord.price, 100);
        Assert.assertEquals(testPriceRecord.date, "2023-07-01");
    }

    /**
     * Helper method to create StockCollection from test data.
     *
     * @param stock The stock for this collection
     * @param priceData Array of [price, date] pairs
     * @return Populated StockCollection
     */
    private static StockCollection makeStockCollection(Stock stock, Object[][] priceData) {
        StockCollection stockCollection = new StockCollection(stock);
        for (Object[] priceRecordData : priceData) {
            PriceRecord priceRecord = new PriceRecord(stock, (int) priceRecordData[0],
                    (String) priceRecordData[1]);
            stockCollection.addPriceRecord(priceRecord);
        }
        return stockCollection;
    }

    /**
     * Tests StockCollection statistical methods.
     */
    public static void testStockCollection() {
        System.out.println("Running testStockCollection");
        Stock testStock = new Stock("AAPL", "Apple Inc.");
        StockCollection stockCollection = new StockCollection(testStock);

        // Test empty collection
        Assert.assertEquals(0, stockCollection.getNumPriceRecords());
        Assert.assertEquals(-1, stockCollection.getMaxPrice());
        Assert.assertEquals(-1, stockCollection.getMinPrice());
        Assert.assertEquals(-1.0, stockCollection.getAvgPrice(), 0.001);

        // Test with data
        Object[][] priceData = {
                { 110, "2023-06-29" },
                { 112, "2023-07-01" },
                { 90, "2023-06-28" },
                { 105, "2023-07-06" }
        };
        testStock = new Stock("AAPL", "Apple Inc.");
        stockCollection = makeStockCollection(testStock, priceData);

        Assert.assertEquals(priceData.length, stockCollection.getNumPriceRecords());
        Assert.assertEquals(112, stockCollection.getMaxPrice());
        Assert.assertEquals(90, stockCollection.getMinPrice());
        Assert.assertEquals(104.25, stockCollection.getAvgPrice(), 0.1);
    }

    /**
     * Tests biggest price change calculation.
     */
    public static void testGetBiggestChange() {
        System.out.println("Running testGetBiggestChange");
        Stock testStock = new Stock("AAPL", "Apple Inc.");
        StockCollection stockCollection = new StockCollection(testStock);

        // Test empty collection
        Assert.assertNull(stockCollection.getBiggestChange());

        // Test positive change
        Object[][] priceData = {
                { 110, "2023-06-29" },
                { 112, "2023-07-01" },
                { 90, "2023-06-25" },
                { 105, "2023-07-06" }
        };
        stockCollection = makeStockCollection(testStock, priceData);
        Assert.assertEquals(new Object[] { 20, "2023-06-25", "2023-06-29" },
                stockCollection.getBiggestChange());

        // Test negative change
        Object[][] priceData2 = {
                { 200, "2000-01-04" },
                { 210, "1999-12-30" },
                { 190, "2000-01-03" },
                { 180, "2000-01-01" }
        };
        stockCollection = makeStockCollection(testStock, priceData2);
        Assert.assertEquals(new Object[] { -30, "1999-12-30", "2000-01-01" },
                stockCollection.getBiggestChange());
    }

    /**
     * Tests Tradebook portfolio calculations.
     */
    public static void testTradebook() {
        System.out.println("Running testTradebook");
        Tradebook tradebook = new Tradebook();
        Stock testStock1 = new Stock("AAPL", "Apple Inc.");
        Object[][] testPriceData1 = {
                { 110, "2023-06-29" },
                { 112, "2023-07-01" },
                { 90, "2023-06-25" },
                { 105, "2023-07-06" }
        };
        StockCollection testStockCollection1 = makeStockCollection(testStock1, testPriceData1);

        ArrayList<StockCollection> testStockCollections = new ArrayList<>();
        testStockCollections.add(testStockCollection1);

        Transaction transaction1 = new Transaction(testStock1, "buy", "2023-06-25", 10);
        tradebook.addTransaction(transaction1);
        // Total: 10 × 105 = 1050
        Assert.assertEquals(1050, tradebook.getTotal(testStockCollections));

        Transaction transaction2 = new Transaction(testStock1, "buy", "2023-06-29", 5);
        Transaction transaction3 = new Transaction(testStock1, "sell", "2023-07-01", 3);
        tradebook.addTransaction(transaction2);
        tradebook.addTransaction(transaction3);
        // Total stocks: 10 + 5 - 3 = 12
        // Total: 12 × 105 = 1260
        Assert.assertEquals(1260, tradebook.getTotal(testStockCollections));

        // Add more stocks for comprehensive test
        Stock testStock2 = new Stock("GOOG", "Alphabet Inc.");
        Stock testStock3 = new Stock("MSFT", "Microsoft Corporation");
        Stock testStock4 = new Stock("AMZN", "Amazon.com Inc.");

        Object[][] testPriceData2 = {
                { 1500, "2023-06-29" }, { 1550, "2023-07-01" },
                { 1475, "2023-06-25" }, { 1520, "2023-07-06" }
        };
        Object[][] testPriceData3 = {
                { 250, "2023-06-29" }, { 255, "2023-07-01" },
                { 245, "2023-06-25" }, { 260, "2023-07-06" }
        };
        Object[][] testPriceData4 = {
                { 3500, "2023-06-29" }, { 3600, "2023-07-01" },
                { 3450, "2023-06-25" }, { 3550, "2023-07-06" }
        };

        testStockCollections.add(makeStockCollection(testStock2, testPriceData2));
        testStockCollections.add(makeStockCollection(testStock3, testPriceData3));
        testStockCollections.add(makeStockCollection(testStock4, testPriceData4));

        // Add transactions for other stocks
        tradebook.addTransaction(new Transaction(testStock2, "buy", "2023-06-25", 15));
        tradebook.addTransaction(new Transaction(testStock2, "sell", "2023-06-29", 10));
        tradebook.addTransaction(new Transaction(testStock2, "sell", "2023-07-01", 1));

        tradebook.addTransaction(new Transaction(testStock3, "sell", "2023-07-01", 5));
        tradebook.addTransaction(new Transaction(testStock3, "buy", "2023-06-29", 20));
        tradebook.addTransaction(new Transaction(testStock3, "buy", "2023-06-25", 10));

        tradebook.addTransaction(new Transaction(testStock4, "buy", "2023-07-01", 5));
        tradebook.addTransaction(new Transaction(testStock4, "buy", "2023-06-29", 5));
        tradebook.addTransaction(new Transaction(testStock4, "buy", "2023-06-25", 1));

        // AAPL: 12 shares × 105 = 1260
        // GOOG: 4 shares × 1520 = 6080
        // MSFT: 25 shares × 260 = 6500
        // AMZN: 11 shares × 3550 = 39050
        // Total: 52890
        Assert.assertEquals(52890, tradebook.getTotal(testStockCollections));
    }

    /**
     * Tests stock type classification and filtering.
     */
    public static void testStockTypes() {
        System.out.println("Testing Stock Types");

        StockRegistry registry = new StockRegistry();

        Stock tech1 = new Stock("AAPL", "Apple Inc.", StockType.TECHNOLOGY);
        Stock tech2 = new Stock("MSFT", "Microsoft Corporation", StockType.TECHNOLOGY);
        Stock finance1 = new Stock("JPM", "JPMorgan Chase", StockType.FINANCE);
        Stock healthcare1 = new Stock("JNJ", "Johnson & Johnson", StockType.HEALTHCARE);

        registry.addStock(tech1);
        registry.addStock(tech2);
        registry.addStock(finance1);
        registry.addStock(healthcare1);

        System.out.println("  Stock 'AAPL' is " + tech1.stockType + " sector");

        ArrayList<Stock> techStocks = registry.getStocksByType(StockType.TECHNOLOGY);
        System.out.println("  Found " + techStocks.size() + " TECHNOLOGY stocks");

        ArrayList<Stock> financeStocks = registry.getStocksByType(StockType.FINANCE);
        System.out.println("  Found " + financeStocks.size() + " FINANCE stock");

        // These assertions will fail until Task #4 is implemented
        // Assert.assertEquals(2, techStocks.size());
        // Assert.assertEquals(1, financeStocks.size());
    }

    /**
     * Tests volatility calculation.
     */
    public static void testVolatility() {
        System.out.println("Testing Volatility");

        Stock testStock = new Stock("AAPL", "Apple Inc.");
        Object[][] priceData = {
                { 90, "2023-06-25" },
                { 110, "2023-06-29" },
                { 112, "2023-07-01" },
                { 105, "2023-07-06" }
        };
        StockCollection stockCollection = makeStockCollection(testStock, priceData);

        double volatility = stockCollection.getVolatility();
        System.out.println("  Volatility for AAPL: " + String.format("%.2f", volatility));

        // Expected volatility calculation:
        // Mean = 104.25
        // Variance = ((90-104.25)² + (110-104.25)² + (112-104.25)² + (105-104.25)²) / 4
        //          = (203.0625 + 33.0625 + 60.0625 + 0.5625) / 4
        //          = 296.75 / 4 = 74.1875
        // Std Dev = sqrt(74.1875) ≈ 8.61

        // This assertion will fail until Task #3 is implemented
        // Assert.assertEquals(8.61, volatility, 0.1);

        // Test edge case: less than 2 records
        Stock testStock2 = new Stock("GOOG", "Alphabet Inc.");
        StockCollection emptyCollection = new StockCollection(testStock2);
        Assert.assertEquals(0.0, emptyCollection.getVolatility(), 0.01);
    }
}

