package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class Artist {
    private static int count = 1;
    private String id;
    private String name;
    private String genre;

    // Constructor
    public Artist() {
        this("", "");
    }
    
    public Artist(String name, String genre) {
        this.id = formatId(count);
        this.name = name;
        this.genre = genre;
        
        count++;
    }
    // Getters
    // Setters
    
    // Methods
    public String formatId(int count) {
        if(count < 10) 
            return "A00" + count;
        else if(count >= 10 && count < 100) 
            return "A0" + count;
        else if(count >= 100 && count < 1000) 
            return "A" + count;
        return "";
    }
}
