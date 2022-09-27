package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class ShowSeat extends VenueSeat {
    private boolean isReserved = false;

    public ShowSeat(VenueSeatCat category, int seatNumber, char row, String section) {
        super(category, seatNumber, row, section);
    }

    public boolean isIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}
