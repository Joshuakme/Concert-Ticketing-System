package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author kohmi
 */
public class Ewallet extends Payment {
    private String host;
    private double balance;
    private double newBalance;
    

    // Constructor
    public Ewallet(String host, double balance, double newBalance, double amount, Date createdOn, PaymentStatus status, String transactionID) {
        super(amount, createdOn, status, transactionID);
        this.host = host;
        this.balance = balance;
        this.newBalance = newBalance;
    }
 
    // Getters

    // Setters

    // Methods++  
    public void updateBalance(double amount) {
        System.out.println("Balance Updated.");
        this.newBalance = this.balance - amount;
        this.balance = this.newBalance;
    }
    
    @Override
    public void pay() {
        System.out.print("Ewallet Paid!");
        // Codes Here
        
        if(this.balance > super.amount) {
            updateBalance(super.amount);
        }
        
    }
}
