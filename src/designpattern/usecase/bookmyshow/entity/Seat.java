package designpattern.usecase.bookmyshow.entity;

public class Seat {
    private int seatId;
    private boolean status;

    public Seat(int seatId, boolean status) {
        this.seatId = seatId;
        this.status = status;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", status=" + status +
                '}';
    }
}
