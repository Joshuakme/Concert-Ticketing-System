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

    // Constructor
    public VenueSeat(VenueSeatCat category, String section, char row, int seatNumber) {
        this.category = category;
        this.seatNumber = seatNumber;
        this.row = row;
        this.section = section;
    }

    // Getter
    public VenueSeatCat getCategory() {
        return category;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public char getRow() {
        return row;
    }

    public String getSection() {
        return section;
    }

    // Methods
    public static String assignSection() {
        String section = "A";

        return section;
    }

    public static char assignRow() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        char assignedRow = alphabet[0];

        return assignedRow;
    }

    public static int assignSeatNo() {
        int seatNo = 0;

        return seatNo;
    }
}
