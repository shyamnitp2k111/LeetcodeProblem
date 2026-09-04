package designpattern.usecase.bookmyshow.entity;

public class Payment {
    private int paymentId;
    private boolean status;

    public Payment(int paymentId, boolean status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", status=" + status +
                '}';
    }
}
