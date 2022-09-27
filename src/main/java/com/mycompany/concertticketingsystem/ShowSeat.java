package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class ShowSeat extends VenueSeat {
    private static int count = 0;
    private ShowSeatCat showSeatCat;
    private boolean reserved;

    // Constructor

    public ShowSeat(ShowSeatCat showSeatCat, VenueSeatCat category, String section, char row, int seatNumber) {
        super(category, seatNumber, row, section);
        this.showSeatCat = showSeatCat;
        this.reserved = false;
    }

    // Getter
    public static int getCount() {
        return count;
    }

    public ShowSeatCat getShowSeatCat() {
        return showSeatCat;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean isReserved) {
        this.reserved = isReserved;
    }

    public String assignSection() {
        String section = "A";
        if (count > 200) {
            section = "A";
        }

        return section;
    }
}
