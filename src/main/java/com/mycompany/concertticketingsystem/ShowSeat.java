package com.mycompany.concertticketingsystem;

/**
 *
 * @author Joshua Koh
 */
public class ShowSeat extends VenueSeat {
    private static int sectionCount = 0;
    private static int rowCount = 0;
    private static int seatCount = 0;
    private ShowSeatCat showSeatCat;
    private boolean reserved;

    // Constructor

    public ShowSeat(ShowSeatCat showSeatCat, VenueSeatCat category) {
        // String assignedSection = assignSection();
        // char assignedRow = assignRow();
        // int assignedSeatNo = assignSeatNo();
        super(category, assignSection(), assignRow(), assignSeatNo());
        this.showSeatCat = showSeatCat;
        this.reserved = false;
    }

    // Getter
    public static int getSectionCount() {
        return sectionCount;
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

    public static String assignSection() {
        // int venueCapacity = showSeatCat.getCatCapapcity();

        String section = "A";

        if (sectionCount <= 2) {
            section = "A101";
        } else if (seatCount > 2) {
            section = "B101";
        }

        return section;
    }

    public static char assignRow() {
        // int sectionCapacity = showSeatCat.getCatCapapcity() / 2;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        char assignedRow = alphabet[0];

        if (rowCount <= 20)
            assignedRow = alphabet[rowCount];
        else {
            sectionCount++;
        }

        return assignedRow;
    }

    public static int assignSeatNo() {
        // int rowCapacity = (showSeatCat.getCatCapapcity() / 2) / 10;
        int seatNo = 0;

        if (seatCount <= 10) {
            seatNo = seatCount;
        } else {
            rowCount++;
            seatNo = rowCount % 10;
        }

        return seatNo;
    }
}
