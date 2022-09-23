package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author kohmi
 */
public class Cash extends Payment {
    private double amountReceived;
    private double amountFinds;

    // Constructor

    public Cash(double amountReceived, double amountFinds, double amount, Date createdOn, PaymentStatus status, String transactionID) {
        super(amount, createdOn, status, transactionID);
        this.amountReceived = amountReceived;
        this.amountFinds = amountFinds;
    }
    
    // Getters
    
    // Setters
   

    // Methods
    @Override
    public void pay() {
        System.out.print("Cash Paid!");
        // Codes Here
    }
}
