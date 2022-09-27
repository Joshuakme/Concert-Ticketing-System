package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh
 */
public class Order {
    private static int count = 0;
    private String orderNumber;
    private int numberOfSeats;
    private LocalDate createdOn;
    private OrderStatus status;
    private Ticket ticket;

    // constructor
    public Order(int numberOfSeats, LocalDate createdOn, Ticket ticket) {
        this.orderNumber = formatId(count);
        this.numberOfSeats = numberOfSeats;
        this.createdOn = createdOn;
        this.status = OrderStatus.PENDING;
        this.ticket = ticket;
    }

    Scanner sc = new Scanner(System.in);

    // Getter
    public String getOrderNumber() {
        return orderNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // Methods
    private String formatId(int count) {
        if (count < 10)
            return "OD000" + count;
        else if (count >= 10 && count < 100)
            return "OD00" + count;
        else if (count >= 100 && count < 1000)
            return "OD0" + count;
        else if (count >= 1000 && count < 10000)
            return "OD" + count;
        else {
            return "OD" + count;
        }
    }

    public void cancelOrder() {
        System.out.println("Are you sure you want to cancel your order? (Y/N)");
        char choice = Character.valueOf(sc.nextLine().charAt(0));

        if (choice == 'y' || choice == 'Y') {
            System.out.println("Your order is cancelled successfully");
            status = OrderStatus.CANCELED;
            displayOrder();
        } else if (choice == 'n' || choice == 'N') {
            System.out.println("Order cancellation is aborted");
        }
    }

    public void displayOrder() {
        if (status != OrderStatus.CANCELED) {
            System.out.println("Order Details");
            System.out.println("----------------------------------");
            System.out.println();
            System.out.println("Your Order Number: " + orderNumber);
            System.out.println("Seats booked: " + numberOfSeats);
            System.out.println("Date purchased: " + createdOn);
            System.out.println("Current tickets status: " + status);
            System.out.println();
            System.out.println("----------------------------------");
            System.out.println("");
            System.out.println("Concert Details");
            System.out.println("----------------------------------");
            System.out.println();
            ticket.concert.displayAllDetail();
            System.out.println("----------------------------------");
        } else {
            System.out.println("Order Details");
            System.out.println("----------------------------------");
            System.out.println();
            System.out.println("Your Order Number: " + orderNumber);
            System.out.println("Seats booked: " + numberOfSeats);
            System.out.println("Date purchased: " + createdOn);
            System.out.println("Current tickets status: " + status);
            System.out.println();
            System.out.println("----------------------------------");
        }

    }

}
