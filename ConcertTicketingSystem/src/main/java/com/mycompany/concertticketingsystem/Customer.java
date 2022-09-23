package com.mycompany.concertticketingsystem;


import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Customer extends Person {
    
    // Constructor
    public Customer() {
        this("", "", "", "", "", LocalDate.now());
    }
        
    public Customer(String firstName, String lastName, String address, String email, String phone, LocalDate joinedDate) {    
        super(firstName, lastName, address, email, phone, joinedDate);
    }

    // Getters
    
    // Setters
    
    // Methods
    private LocalDate getCurrentDate() {
        LocalDate now = LocalDate.now();
        
        return now;
    }
    
    private boolean buyTicket() {
        boolean isSuccessful = false;
        
        return isSuccessful;
    }
    
    private Order getOrder() {
        Order orderDetail = new Order();
        
        return orderDetail;
    }
}
