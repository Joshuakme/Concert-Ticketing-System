package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author kohmi
 */
public class Coupon {
    private int id;
    private double balance;
    private Date expiryDate;

    // Constructor
    public Coupon(int id, double balance, Date expiryDate) {
        this.id = id;
        this.balance = balance;
        this.expiryDate = expiryDate;
    }
    
    
}
