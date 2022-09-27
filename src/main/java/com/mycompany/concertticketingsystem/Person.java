package com.mycompany.concertticketingsystem;

// Imports
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public Person(Account account, String firstName, String lastName, String address, String email, String phone) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.joinedDate = getCurrentDate();
    }

    public static LocalDate getCurrentDate() {
        LocalDate today = LocalDate.now();
        return today;
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
