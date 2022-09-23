package com.mycompany.concertticketingsystem;

// Imports
import java.time.LocalDate;

/**
 *
 * @author Joshua_Koh
 * @date 2022-09-15
 */
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private LocalDate joinedDate;

    // Constructor
    public Person() {
        this("", "", "", "", "", LocalDate.now());
    }
    
    public Person(String firstName, String lastName, String address, String email, String phone, LocalDate joinedDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.joinedDate = joinedDate;
    }
    
    // Getters

    // Setters
    
    // Methods


}
