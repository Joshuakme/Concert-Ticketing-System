package com.mycompany.concertticketingsystem;

// Imports
import java.time.LocalDate;

/**
 *
 * @author Joshua_Koh
 * @date 2022-09-15
 */
public class Person {
    private Account account;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private LocalDate joinedDate;

    // Constructor    
    public Person(Account account, String firstName, String lastName, String address, String email, String phone, LocalDate joinedDate) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.joinedDate = joinedDate;
    }

    
    
    // Getters
    public Account getAccount() {
        return account;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }
    

    // Setters
    
    // Methods




}
