package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
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
    public Ewallet(String host, double balance, LocalDate createdOn) {
        super(createdOn);
        this.host = host;
        this.balance = balance;
        this.newBalance = balance;
    }

    // Getters

    // Setters

    // Methods++
    public void updateBalance(double amount) {
        System.out.println("Balance Updated.");
        this.newBalance = amount;
        this.balance = this.newBalance;
    }

    @Override
    public boolean pay(double payAmount) {
        if (this.balance >= payAmount) {
            System.out.println("Successfully paid by Ewallet - " + this.host + "!");
            this.updateBalance(this.balance - payAmount);
            return true;
        } else {
            System.err.println("Insufficient balance. Please try again.");
            System.out.println("");
            return false;
        }

    }
}
