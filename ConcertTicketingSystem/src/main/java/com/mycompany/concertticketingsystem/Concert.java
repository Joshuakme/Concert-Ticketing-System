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
public class Concert {
    private static int count = 1;
    private String id;
    private String name;
    private Artist artist;
    private Date datetime;
    private String language;
    private Venue venue;

    // Constructor
    public Concert() {
        this("", new Artist(), new Date(), "", new Venue());
    }
    
    public Concert(String name, Artist artist, Date datetime, String language, Venue venue) {
        this.id = formatId(count);
        this.name = name;
        this.artist = artist;
        this.datetime = datetime;
        this.language = language;
        this.venue = venue;
        
        count++;   
    }
    
    // Getters
    
    // Setters
    
    // Methods
    private String formatId(int count) {
        if(count < 10) 
            return "C000" + count;
        else if(count >= 10 && count < 100) 
            return "C00" + count;
        else if(count >= 100 && count < 1000) 
            return "C0" + count;
        else if(count >= 1000 && count < 10000)
            return "C" + count;
        return "";
    }
    
    public void getShow() {
        // Codes Here
    }
}
