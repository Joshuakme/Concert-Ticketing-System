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
    private int remainingSeat;

    // Constructor
    public TicketCat(String description, double price, int remainingSeat) {
        this.description = description;
        this.price = price;
        this.remainingSeat = remainingSeat;
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
