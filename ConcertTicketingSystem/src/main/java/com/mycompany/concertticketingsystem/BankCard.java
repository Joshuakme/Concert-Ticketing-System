package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author kohmi
 */
public class BankCard extends Payment {
    private String nameOnCard;
    private String cardType;    // "Credit", "Debit"
    private int cardNum;
    private Date validDate;
    private int cvc;
    private double balance;
    
    // Constructor
    public BankCard(String nameOnCard, String cardType, int cardNum, Date validDate, int cvc, double amount, Date createdOn, PaymentStatus status, String transactionID) {
        super(amount, createdOn, status, transactionID);
        this.nameOnCard = nameOnCard;
        this.cardType = cardType;
        this.cardNum = cardNum;
        this.validDate = validDate;
        this.cvc = cvc;
    }
    
    
    
    // No Getters & Setters (No information about Credit Card should be retrieved)
    
    // Methods
    @Override
    public void pay() {
        System.out.print("Credit Card Paid!");
        // Codes Here
    }
    
}
