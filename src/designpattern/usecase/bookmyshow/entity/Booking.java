package designpattern.usecase.bookmyshow.entity;

import java.util.List;

public class Booking {
    private int bookingId;
    private int amount;
    private List<Seat> seat;
    private Show show;
    private int transactionId;
    private int userId;


    public Booking(int bookingId, int amount, List<Seat> seat, Show show, int transactionId, int userId) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.seat = seat;
        this.show = show;
        this.transactionId = transactionId;
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Seat> getSeat() {
        return seat;
    }

    public void setSeat(List<Seat> seat) {
        this.seat = seat;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
