package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class Venue {
    private String id;
    private String name;
    private String address;
    private String type;
    private int capacity;

    // Constructor
    public Venue() {
        this("", "", "" , "", 0);
    }
    
    public Venue(String id, String name, String location, String type, int capacity) {
        this.id = id;
        this.name = name;
        this.address = location;
        this.type = type;
        this.capacity = capacity;
    }
    
    // Getters
    
    // Setters
    
    // Methods
}
