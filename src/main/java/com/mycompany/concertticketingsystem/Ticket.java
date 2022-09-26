package com.mycompany.concertticketingsystem;

import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Ticket {
    private String serialNo;
    private String concertId;
    private ShowSeat seat;
    private LocalDate purchaseDate;

    // Constructor
    public Ticket(String serialNo, String concertId, ShowSeat seat, LocalDate purchaseDate) {
        this.serialNo = serialNo;
        this.concertId = concertId;
        this.seat = seat;
        this.purchaseDate = purchaseDate;
    }
    
    // Getters

    // Setters



}
