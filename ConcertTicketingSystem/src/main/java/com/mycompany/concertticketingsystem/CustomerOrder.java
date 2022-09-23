/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concertticketingsystem;

import java.util.Date;

/**
 *
 * @author Joshua Koh
 */
public class CustomerOrder {
    private Date orderTime;
    private String deliveryAddress;
    private String deliveryEmail;
    private Date preferredDeliveryTime;
    private Date timePaid;
    private Date timeSent;
    private int ticketQty;
    private double totalPrice;
    private double discount;

    // Constructor
    public CustomerOrder(Date orderTime, String deliveryAddress, String deliveryEmail, Date preferredDeliveryTime, Date timePaid, Date timeSent, int ticketQty, double totalPrice, double discount) {
        this.orderTime = orderTime;
        this.deliveryAddress = deliveryAddress;
        this.deliveryEmail = deliveryEmail;
        this.preferredDeliveryTime = preferredDeliveryTime;
        this.timePaid = timePaid;
        this.timeSent = timeSent;
        this.ticketQty = ticketQty;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }
    
    
    // Getters
    
    // Setters
    
    
    // Methods
    public double getFinalPrice() {
        return this.totalPrice * this.discount;
    }
}
