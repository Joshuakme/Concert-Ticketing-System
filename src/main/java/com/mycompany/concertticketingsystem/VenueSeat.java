package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class VenueSeat {
    private VenueSeatCat category;
    private int seatNumber; // column
    private char row;
    private String section;

    public VenueSeat(VenueSeatCat category, int seatNumber, char row, String section) {
        this.category = category;
        this.seatNumber = seatNumber;
        this.row = row;
        this.section = section;
    }

}
