package com.mycompany.concertticketingsystem;

/**
 *
 * @author kohmi
 */
public class Account {
    private String username;
    private String password;
    private String acountStatus;
    
    // Constructor
    public Account(String username, String password, String acountStatus) {
        this.username = username;
        this.password = password;
        this.acountStatus = acountStatus;
    }
    
    // Getters
    
    // Setters
    
    // Methods
    public boolean resetPassword() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
}
