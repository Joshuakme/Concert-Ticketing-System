package com.mycompany.concertticketingsystem;

import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Ticket {
    private String serialNo;
    public Concert concert;
    private ShowSeat seat;
    private LocalDate purchaseDate;

    // Constructor
    public Ticket(String serialNo, Concert concert, ShowSeat seat, LocalDate purchaseDate) {
        this.serialNo = serialNo;
        this.concert = concert;
        this.seat = seat;
        this.purchaseDate = purchaseDate;
    }

    // Getters

    // Setters

}
