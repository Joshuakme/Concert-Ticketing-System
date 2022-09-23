/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author Joshua Koh
 */
public class TicketCat {
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
