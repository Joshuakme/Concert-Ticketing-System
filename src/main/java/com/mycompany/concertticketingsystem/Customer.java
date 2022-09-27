package com.mycompany.concertticketingsystem;

import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Customer extends Person {

    // Constructor
    public Customer(Account account, String firstName, String lastName, String address, String email, String phone) {
        super(account, firstName, lastName, address, email, phone);
    }

    // Getters
    // Setters
    // Methods

    private boolean buyTicket() {
        boolean isSuccessful = false;

        return isSuccessful;
    }

    // private Order getOrder() {
    // Order orderDetail = new Order();

    // return orderDetail;
    // }
}
