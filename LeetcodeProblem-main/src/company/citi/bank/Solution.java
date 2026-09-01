package company.citi.bank;


/**
  We are developing a banking transaction monitoring system that tracks accounts, transactions, and detects patterns.

  The program includes three classes: `Account`, `Transaction`, and `TransactionMonitor`.

 * Classes:
  The `Account` class represents a bank account.
  The `Transaction` class holds information about a single transaction.
  The `TransactionMonitor` class manages all transactions and provides fraud detection methods.

  To begin with, we present you with two tasks:
  1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
  1-2) The test for TransactionMonitor is not passing due to a bug in the code. Make the necessary changes to TransactionMonitor to fix the bug.

  2) We want to add a new function called "detectLargeWithdrawalPattern" to the TransactionMonitor class.
  This function detects if an account has made multiple large withdrawals within a short time period,
  which could indicate suspicious activity.

 *The function should return true if:
  - There are 3 or more withdrawals of $5000 or more
  - All occurring within the same calendar month

 Otherwise, return false.

 For example:
 Transactions for account A001:
  - 2024-02-05: Withdrawal $6000
  - 2024-02-10: Withdrawal $5500
  - 2024-02-15: Withdrawal $7000
 → Returns true (3 large withdrawals in February)

  Transactions for account A002:
  - 2024-02-05: Withdrawal $6000
  - 2024-03-10: Withdrawal $5500
  → Returns false (only 2 withdrawals, and in different months)

  To assist you in testing this new function, we have provided the testDetectLargeWithdrawalPattern function.
 */


import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
        import java.util.stream.Collectors;


class Account {
    /**
     * Data about a bank account.
     */
    String accountNumber;
    String accountHolder;
    String accountType;     // "Checking", "Savings", "Credit"

    Account(String accountNumber, String accountHolder, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Account account = (Account) other;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

class Transaction {
    /**
     * Data about a bank transaction.
     */
    Account account;
    String type;            // "Deposit", "Withdrawal", "Transfer"
    double amount;
    String date;            // Format: "YYYY-MM-DD"
    String time;            // Format: "HH:MM"

    Transaction(Account account, String type, double amount, String date, String time) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }
}

class TransactionMonitor {
    /**
     * Manages transactions and provides fraud detection capabilities.
     */
    ArrayList<Transaction> transactions = new ArrayList<>();

    TransactionMonitor() {
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    int getTotalTransactions() {
        return transactions.size();
    }

    double getTotalDeposits() {
        return transactions.stream()
                .filter(t -> t.type.equals("Deposit"))
                .mapToDouble(t -> t.amount)
                .sum();
    }

    double getTotalWithdrawals() {
        return transactions.stream()
                .filter(t -> t.type.equals("Withdrawal"))
                .mapToDouble(t -> t.amount)
                .sum();
    }

    double getAverageTransactionAmount() {
        /**
         * Returns the average transaction amount.
         * BUG: This method has a bug - fix it!
         */
        /*if(transactions.isEmpty()) {
            return 0.0;
        }*/
        return transactions.stream().mapToDouble(tran -> tran.amount).average().orElse(0.0);
    }

    int getTransactionCountForAccount(String accountNumber) {
        /** Returns the count of transactions for a specific account. */
        return (int) transactions.stream()
                .filter(t -> t.account.accountNumber.equals(accountNumber))
                .count();
    }

    /**
     * Approach:
     * <p>
     * 1. I will iterate through all transactions.
     * 2. I will filter only those transactions that belong to the given account.
     * 3. From those, I will consider only withdrawals with amount >= 5000.
     * 4. I will group these qualifying transactions by month using a HashMap (key = "YYYY-MM").
     * 5. For each transaction, I will increment the count of that month.
     * 6. If at any point the count for a month reaches 3, I will return true immediately (early exit optimization).
     * 7. If no such pattern is found after processing all transactions, I will return false.
     */

    public boolean detectLargeWithdrawalPatterns(String accountNumber) {

        /** Map to store count of withdrawals per month */
        Map<String, Integer> monthCount = new HashMap<>();

        /** Step 1: Iterate over all transactions */
        for (Transaction transaction : transactions) {
            /** Step 2: Filter by account number */
            if (!transaction.account.accountNumber.equals(accountNumber)) continue;

            /** Step 3: Consider only withdrawals >= 5000 */
            if (!transaction.type.equals("Withdrawal") || transaction.amount < 5000.0) continue;

            /** Step 4: Extract month (format: YYYY-MM) */
            String month = transaction.date.substring(0, 7);

            /** Step 5: Increment count for this month */
            int count = monthCount.getOrDefault(month, 0) + 1;
            monthCount.put(month, count);

            /** Step 6: Check condition immediately (optimization) */
            if (count >= 3) {
                return true; /** found suspicious patter */
            }
        }
        /** Step 7: No pattern detected */
        return false;
    }

    public boolean detectLargeWithdrawalPattern(String accountNumber) {
      
         return transactions.stream().
                 filter(t -> t.account.accountNumber.equals(accountNumber) && t.type.equals("Withdrawal"))
                 .filter(t -> t.amount >= 5000)
                 .collect(Collectors.groupingBy(t -> t.date.substring(0, t.date.lastIndexOf("-")), Collectors.counting()))
                 .values()
                 .stream()
                 .anyMatch(i -> i >=3);
    }

/*    public boolean detectLargeWithdrawalPattern(String accountNumber) {
        // Write your code here to solve this problem
        List<Transaction> filtered = transactions.stream()
                .filter(transaction -> transaction.account.accountNumber.equals(accountNumber))
                .filter(transaction -> transaction.type.equals("Withdrawal"))
                .filter(transaction -> transaction.amount >= 5000)
                .collect(Collectors.toList());

        if (filtered.size() < 3) return false;

        // Group by month (YYYY-MM)
        Map<String, Long> monthCount = filtered.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.date.substring(0,7),
                        Collectors.counting()));

        //heck if any month has >= 3 withdrawals
        return monthCount.values().stream().anyMatch(count -> count >= 3);
    }*/
}

public class Solution {
    public static void main(String[] args) {
        testTransaction();
        testTransactionMonitor();
        testDetectLargeWithdrawalPattern();
    }

    public static void testTransaction() {
        System.out.println("Running testTransaction");
        Account account = new Account("A001", "John Doe", "Checking");
        Transaction transaction = new Transaction(account, "Deposit", 1000.0, "2024-02-01", "10:30");

        Assert.assertEquals(account, transaction.account);
        Assert.assertEquals("Deposit", transaction.type);
        Assert.assertEquals(1000.0, transaction.amount, 0.01);
    }

    public static void testTransactionMonitor() {
        System.out.println("Running testTransactionMonitor");
        TransactionMonitor monitor = new TransactionMonitor();

        Assert.assertEquals(0, monitor.getTotalTransactions());
        Assert.assertEquals(0.0, monitor.getTotalDeposits(), 0.01);
        Assert.assertEquals(0.0, monitor.getAverageTransactionAmount(), 0.01);

        Account acc1 = new Account("A001", "Alice", "Checking");
        Account acc2 = new Account("A002", "Bob", "Savings");

        Transaction t1 = new Transaction(acc1, "Deposit", 1000.0, "2024-02-01", "10:00");
        Transaction t2 = new Transaction(acc1, "Withdrawal", 500.0, "2024-02-02", "11:00");
        Transaction t3 = new Transaction(acc2, "Deposit", 2000.0, "2024-02-03", "12:00");

        monitor.addTransaction(t1);
        monitor.addTransaction(t2);
        monitor.addTransaction(t3);

        Assert.assertEquals(3, monitor.getTotalTransactions());
        Assert.assertEquals(3000.0, monitor.getTotalDeposits(), 0.01);
        Assert.assertEquals(500.0, monitor.getTotalWithdrawals(), 0.01);
        Assert.assertEquals(1166.67, monitor.getAverageTransactionAmount(), 0.01);
        Assert.assertEquals(2, monitor.getTransactionCountForAccount("A001"));
    }

    public static void testDetectLargeWithdrawalPattern() {
        System.out.println("Running testDetectLargeWithdrawalPattern");
        TransactionMonitor monitor = new TransactionMonitor();

        Account acc1 = new Account("A001", "Alice", "Checking");
        Account acc2 = new Account("A002", "Bob", "Savings");

        // Account A001: 3 large withdrawals in same month
        Transaction t1 = new Transaction(acc1, "Withdrawal", 6000.0, "2024-02-05", "10:00");
        Transaction t2 = new Transaction(acc1, "Withdrawal", 5500.0, "2024-02-10", "11:00");
        Transaction t3 = new Transaction(acc1, "Withdrawal", 7000.0, "2024-02-15", "12:00");

        // Account A002: 2 large withdrawals in different months
        Transaction t4 = new Transaction(acc2, "Withdrawal", 6000.0, "2024-02-05", "13:00");
        Transaction t5 = new Transaction(acc2, "Withdrawal", 5500.0, "2024-03-10", "14:00");

        monitor.addTransaction(t1);
        monitor.addTransaction(t2);
        monitor.addTransaction(t3);
        monitor.addTransaction(t4);
        monitor.addTransaction(t5);

        Assert.assertTrue(monitor.detectLargeWithdrawalPattern("A001"));
        
        System.out.println("monitor detectLargeWithdrawalPattern is " + monitor.detectLargeWithdrawalPattern("A002"));
        Assert.assertFalse(monitor.detectLargeWithdrawalPattern("A002"));
    }
}

