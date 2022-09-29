package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author kohmi
 */
public class Cash extends Payment {
    private double amountReceived;
    private double amountFinds;

    // Constructor
    public Cash(double amountReceived, double amountFinds, LocalDate createdOn) {
        super(createdOn);
        this.amountReceived = amountReceived;
        this.amountFinds = amountFinds;
    }

    // Getters
    public double getAmountReceived() {
        return amountReceived;
    }

    public double getAmountFinds() {
        return amountFinds;
    }

    // Setters
    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public void setAmountFinds(double amountFinds) {
        this.amountFinds = amountFinds;
    }

    // Methods
    @Override
    public boolean pay(double payAmount) {
        if (this.amountReceived >= payAmount) {
            this.amountFinds = amountReceived - payAmount;

            System.out.println("Change due: RM " + String.format(".2f", this.amountFinds));
            System.out.print("Cash Paid!");
            System.out.println("");
            return true;
        } else {
            System.err.println("Insufficient Balance. Please try again.");
            System.out.println("");
            return false;
        }
    }

}
