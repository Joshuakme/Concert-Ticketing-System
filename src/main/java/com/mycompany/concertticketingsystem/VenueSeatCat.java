/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concertticketingsystem;

/**
 *
 * @author Tiffany
 */
public class VenueSeatCat {
    private Venue venue;
    private String description;
    private int catCapapcity;
    
    // Constructor
    public VenueSeatCat(String description, int catCapapcity) {
        this.description = description;
        this.catCapapcity = catCapapcity;
    }
    
    // Getters
    public Venue getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public int getCatCapapcity() {
        return catCapapcity;
    }
    
}
