package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author Joshua Koh
 */
public class TicketCat {
    // CAT 1, ... , CAT 6 (from High to Low)
    private String description;
    private double price;
    private Date startDate;
    private Date endDate;
    private String area;

    // Constructor
    public TicketCat(String description, double price, Date startDate, Date endDate, String area) {
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.area = area;
    }

    // Getters
    
    // Setters
}
