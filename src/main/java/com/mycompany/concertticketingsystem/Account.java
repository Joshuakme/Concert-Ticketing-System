package com.mycompany.concertticketingsystem;

/**
 *
 * @author kohmi
 */
public class Account {
    private String username;
    private String password;
    private AccountStatus acountStatus;
    
    // Constructor
    public Account(String username, String password, AccountStatus acountStatus) {
        this.username = username;
        this.password = password;
        this.acountStatus = acountStatus;
    }
    public String getUsername() {    
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Getters
    public AccountStatus getAcountStatus() {    
        return acountStatus;
    }

    // Setters
    // Methods
    public boolean resetPassword() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
}
