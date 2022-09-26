package com.mycompany.concertticketingsystem;


import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Ticket {
    private String serielNo;
    private String concertId;
    private String seat;
    private LocalDate purchaseDate;

    // Constructor
    public Ticket(String serielNo, String concertId, String seat, LocalDate purchaseDate) {
            this.serielNo = serielNo;
            this.concertId = concertId;
            this.seat = seat;
            this.purchaseDate = purchaseDate;
    }
    
    // Getters
    
    // Setters

   
}
