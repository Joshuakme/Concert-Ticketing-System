package com.mycompany.concertticketingsystem;

import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Ticket {
    private static int count = 0;
    private String serialNo;
    public Concert concert;
    private ShowSeat seat;
    private LocalDate purchaseDate;

    // Constructor
    public Ticket(Concert concert, ShowSeat seat, LocalDate purchaseDate) {
        this.serialNo = formatId(count);
        this.concert = concert;
        this.seat = seat;
        this.purchaseDate = purchaseDate;
    }

    // Getters
    public static int getCount() {
        return count;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public Concert getConcert() {
        return concert;
    }

    public ShowSeat getSeat() {
        return seat;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    // Setters

    // Methods
    private String formatId(int count) {
        if (count < 10)
            return "T000" + count;
        else if (count >= 10 && count < 100)
            return "T00" + count;
        else if (count >= 100 && count < 1000)
            return "T0" + count;
        else if (count >= 1000 && count < 10000)
            return "T" + count;
        else
            return "T" + count;
    }

}
