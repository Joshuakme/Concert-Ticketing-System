package com.mycompany.concertticketingsystem;

import java.time.LocalDate;

/**
 *
 * @author Joshua_Koh
 */
public class Admin extends Person {
    
    // Constructor   
    public Admin() {
        this("", "", "", "", "", LocalDate.now());
    }
    
    public Admin(String firstName, String lastName, String address, String email, String phone, LocalDate joinedDate) {
        super(firstName, lastName, address, email, phone, joinedDate);
    }

    // Getters
    
    // Setters

    // Methods
    public boolean addConcert() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
    
    public boolean addShow() {
        boolean isSuccessful = false;

        return isSuccessful;
    }
    
    public boolean blockUser() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
    
    public boolean unblockUser() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
}
