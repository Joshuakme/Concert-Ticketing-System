/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concertticketingsystem;

/**
 *
 * @author Tiffany
 */
public class ShowSeatCat extends VenueSeatCat{
    private int remainingSeat;
    private double seatPrice;

    public ShowSeatCat(String description, int catCapacity, double seatPrice) {
        super(description, catCapacity);
        this.remainingSeat = catCapacity;
        this.seatPrice = seatPrice;
    }
}
