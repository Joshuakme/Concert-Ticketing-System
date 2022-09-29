package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author kohmi
 */
public abstract class Payment {
    private static int count = 0;
    private LocalDate createdOn;
    private PaymentStatus status;
    private String transactionID;

    // Constructor
    protected Payment(LocalDate createdOn) {
        this.createdOn = createdOn;
        this.status = PaymentStatus.PENDING;
        this.transactionID = formatId(count);
        count++;
    }

    // Getters
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    // Setters
    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    // Methods
    public abstract boolean pay(double payAmount);

    private String formatId(int count) {
        if (count < 10)
            return "P000" + count;
        else if (count >= 10 && count < 100)
            return "P00" + count;
        else if (count >= 100 && count < 1000)
            return "P0" + count;
        else if (count >= 1000 && count < 10000)
            return "P" + count;
        return "";
    }
}
