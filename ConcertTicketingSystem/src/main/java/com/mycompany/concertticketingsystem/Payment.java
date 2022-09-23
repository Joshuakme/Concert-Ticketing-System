package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author kohmi
 */
public abstract class Payment {
    protected double amount;
    protected Date createdOn;
    protected PaymentStatus status;
    protected String transactionID;

    // Constructor
    protected Payment(double amount, Date createdOn, PaymentStatus status, String transactionID) {
        this.amount = amount;
        this.createdOn = createdOn;
        this.status = status;
        this.transactionID = transactionID;
    }

    // Getters

    // Setters
    
    // Methods
    public abstract void pay();
}
