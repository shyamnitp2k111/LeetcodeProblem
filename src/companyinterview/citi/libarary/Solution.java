package companyinterview.citi.libarary;

/*
We are developing a library management system that tracks books, members, and their borrowing history.

The program includes three classes: `Book`, `BorrowRecord`, and `MemberAccount`.

Classes:
* The `Book` class represents data about a specific book in the library.
* The `BorrowRecord` class holds information about a single book borrowing transaction.
* The `MemberAccount` class manages a member's borrowing records and provides statistics.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for MemberAccount is not passing due to a bug in the code. Make the necessary changes to MemberAccount to fix the bug.
*/

/*
2) We want to add a new function called "getMostBorrowedGenre" to the MemberAccount class. 
This function analyzes all the books borrowed by a member and returns the genre that has been 
borrowed most frequently along with the count. If there's a tie, return the genre that appears 
first alphabetically.

For example, if a member has borrowed:
- "The Hobbit" (Fantasy)
- "1984" (Science Fiction) 
- "Dune" (Science Fiction)
- "Harry Potter" (Fantasy)
- "Foundation" (Science Fiction)

The function should return ["Science Fiction", 3] because Science Fiction books were borrowed 
3 times, which is more than any other genre.

To assist you in testing this new function, we have provided the testGetMostBorrowedGenre function.
*/

import java.util.*;
import java.util.stream.Collectors;

import org.junit.*;

class Book {
    /** Data about a particular book. */
    String isbn;        // String, the ISBN of the book
    String title;       // String, the title of the book
    String author;      // String, the author of the book
    String genre;       // String, the genre of the book

    Book(String isbn, String title, String author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Book book = (Book) other;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

class BorrowRecord {
    /** Data about a single book borrowing transaction. */
    Book book;              // Book object representing the borrowed book
    String borrowDate;      // String, the date when the book was borrowed (YYYY-MM-DD)
    String returnDate;      // String, the date when the book was returned (YYYY-MM-DD), null if not returned
    
    BorrowRecord(Book book, String borrowDate, String returnDate) {
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}

class MemberAccount {
    /**
     * Data for a member's borrowing history and methods for getting useful statistics.
     */
    ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();
    String memberId;
    String memberName;

    MemberAccount(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    int getTotalBorrowedBooks() {
        /** Returns the total number of books borrowed by this member. */
        return borrowRecords.size();
    }

    void addBorrowRecord(BorrowRecord record) {
        /** Adds a borrow record to this member's account. */
        borrowRecords.add(record);
    }

    int getCurrentlyBorrowedCount() {
        /** Returns the count of books currently borrowed (not yet returned). */
        return (int) borrowRecords.stream()
            .filter(record -> record.returnDate == null)
            .count();
    }

    int getReturnedCount() {
        /** Returns the count of books that have been returned. */
        return (int) borrowRecords.stream()
            .filter(record -> record.returnDate != null)
            .count();
    }

    double getAverageHoldingDays() {
        /** 
         * Returns the average number of days books were held by the member.
         * Only considers books that have been returned.
         * BUG: This method has a bug - fix it!
         */
        long totalDays = borrowRecords.stream()
            .filter(record -> record.returnDate != null)
            .mapToLong(record -> {
                // Simple day calculation (assuming YYYY-MM-DD format)
                String[] borrowParts = record.borrowDate.split("-");
                String[] returnParts = record.returnDate.split("-");
                int borrowDay = Integer.parseInt(borrowParts[2]);
                int returnDay = Integer.parseInt(returnParts[2]);
                return returnDay - borrowDay;  // Simplified calculation
            })
            .sum();
        
        // BUG: What happens if no books have been returned?
        System.out.println("total days is ... "+ totalDays);
        System.out.println("return count is ....... "+ getReturnedCount());
        double value =  (double) totalDays / getReturnedCount();

        if(Double.isNaN(value)) {
            return 0.0;
        } else {
            return value;
        }
    }

    public Object[] getMostBorrowedGenre() {
        // Write your code here to solve this problem

        Optional<Map.Entry<String, Long>> entry  = borrowRecords.stream()
                .collect(Collectors.groupingBy(br -> br.book.genre, Collectors.counting()))
                .entrySet().stream().max((e1,e2) -> {

                    int value = Long.compare(e1.getValue(), e2.getValue());
                    if(value == 0) {
                        return e1.getKey().compareTo(e2.getKey());
                    } else {
                        return value;
                    }
        });

        System.out.println(entry);

        if(entry.isPresent()) {
            Object[] answer = {entry.get().getKey(), entry.get().getValue().intValue()};
            return answer;
        } else {
            return null;
        }
    }
}

public class Solution {
    public static void main(String[] args) {
        testBorrowRecord();
        testMemberAccount();
        testGetMostBorrowedGenre();
    }

    public static void testBorrowRecord() {
        System.out.println("Running testBorrowRecord");
        Book book = new Book("978-0-13-468599-1", "The Hobbit", "J.R.R. Tolkien", "Fantasy");
        BorrowRecord record = new BorrowRecord(book, "2024-01-15", "2024-01-20");

        Assert.assertEquals(book, record.book);
        Assert.assertEquals("2024-01-15", record.borrowDate);
        Assert.assertEquals("2024-01-20", record.returnDate);
    }

    private static MemberAccount makeMemberAccount(String memberId, String memberName, Object[][] borrowData) {
        MemberAccount account = new MemberAccount(memberId, memberName);
        for (Object[] data : borrowData) {
            Book book = (Book) data[0];
            BorrowRecord record = new BorrowRecord(book, (String) data[1], (String) data[2]);
            account.addBorrowRecord(record);
        }
        return account;
    }

    public static void testMemberAccount() {
        System.out.println("Running testMemberAccount");
        MemberAccount account = new MemberAccount("M001", "John Doe");

        Assert.assertEquals(0, account.getTotalBorrowedBooks());
        Assert.assertEquals(0, account.getCurrentlyBorrowedCount());
        Assert.assertEquals(0.0, account.getAverageHoldingDays(), 0.001);

        Book book1 = new Book("978-1", "Book 1", "Author 1", "Fiction");
        Book book2 = new Book("978-2", "Book 2", "Author 2", "Science");
        Book book3 = new Book("978-3", "Book 3", "Author 3", "Fiction");

        Object[][] borrowData = {
            {book1, "2024-01-01", "2024-01-10"},
            {book2, "2024-01-05", "2024-01-12"},
            {book3, "2024-01-08", null}
        };
        account = makeMemberAccount("M001", "John Doe", borrowData);

        Assert.assertEquals(3, account.getTotalBorrowedBooks());
        Assert.assertEquals(1, account.getCurrentlyBorrowedCount());
        Assert.assertEquals(2, account.getReturnedCount());
        Assert.assertEquals(8.0, account.getAverageHoldingDays(), 0.1);
    }

    public static void testGetMostBorrowedGenre() {
        System.out.println("Running testGetMostBorrowedGenre");
        MemberAccount account = new MemberAccount("M001", "John Doe");

        Assert.assertNull(account.getMostBorrowedGenre());

        Book book1 = new Book("978-1", "The Hobbit", "Tolkien", "Fantasy");
        Book book2 = new Book("978-2", "1984", "Orwell", "Science Fiction");
        Book book3 = new Book("978-3", "Dune", "Herbert", "Science Fiction");
        Book book4 = new Book("978-4", "Harry Potter", "Rowling", "Fantasy");
        Book book5 = new Book("978-5", "Foundation", "Asimov", "Science Fiction");

        Object[][] borrowData = {
            {book1, "2024-01-01", "2024-01-10"},
            {book2, "2024-01-05", "2024-01-12"},
            {book3, "2024-01-08", "2024-01-15"},
            {book4, "2024-01-10", null},
            {book5, "2024-01-12", null}
        };
        account = makeMemberAccount("M002", "Jane Smith", borrowData);

        Assert.assertArrayEquals(new Object[] {"Science Fiction", 3}, account.getMostBorrowedGenre());
    }
}
