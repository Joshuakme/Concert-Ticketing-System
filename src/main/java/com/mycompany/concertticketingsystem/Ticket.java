package com.mycompany.concertticketingsystem;


import java.time.LocalDate;

/**
 *
 * @author Joshua Koh
 */
public class Ticket {
    private String serialNo;
    private String concertId;
<<<<<<< HEAD
    private ShowSeat seat;
    private Date purchaseDate;
=======
    private String seat;
    private LocalDate purchaseDate;
>>>>>>> 3b26699bb5cb01003a8d28ac18225d598d2cd0b0

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
