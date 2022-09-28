package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author kohmi
 */
public class BankCard extends Payment {
    private String nameOnCard;
    private String cardType; // "Credit", "Debit"
    private int cardNum;
    private String validDate;
    private int cvc;
    private double balance;
    private double newBalance;

    // Constructor
    public BankCard(String nameOnCard, String cardType, int cardNum, String validDate, int cvc, double balance,
            LocalDate createdOn) {
        super(createdOn);
        this.nameOnCard = nameOnCard;
        this.cardType = cardType;
        this.cardNum = cardNum;
        this.validDate = validDate;
        this.cvc = cvc;
        this.balance = balance;
        this.newBalance = balance;
    }

    // No Getters & Setters (No information about Credit Card should be retrieved)

    // Methods
    public String getCardLastFourDigit() {
        String cardNum = String.valueOf(this.cardNum);

        int size = cardNum.length();

        String lastFourDigit = String.valueOf(
                cardNum.charAt(size - 3) + cardNum.charAt(size - 2) + cardNum.charAt(size - 1) + cardNum.charAt(size));

        return lastFourDigit;
    }

    public void updateBalance(double amount) {
        System.out.println("Balance Updated.");
        this.newBalance = this.balance - amount;
        this.balance = this.newBalance;
    }

    @Override
    public boolean pay(double payAmount) {
        if (this.balance >= payAmount) {
            System.out.println("Successfully paid by BankCard(" + getCardLastFourDigit() + ")!");
            this.updateBalance(this.balance - payAmount);
            return true;
        } else {
            System.err.println("Insufficient balance. Please try again.");
            System.out.println("");
            return false;
        }

    }
}
