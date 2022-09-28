package com.mycompany.concertticketingsystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joshua Koh
 */
public class Customer extends Person {
    private List<Order> orderArr;
    private Payment payment;

    // Constructor
    public Customer(List<Order> orderArr, Payment payment, Account account, String firstName, String lastName,
            String address, String email, String phone) {
        super(account, firstName, lastName, address, email, phone);
        this.orderArr = orderArr;
        this.payment = payment;
    }

    // Getters
    public List<Order> getOrderArr() {
        return orderArr;
    }

    public Payment getPayment() {
        return payment;
    }

    // Setters
    public void setOrderArr(List<Order> orderArr) {
        this.orderArr = orderArr;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // Methods
    public void makeNewOrder(Order newOrder) {
        List<Order> orderList = new ArrayList<>();

        orderList.add(newOrder);
    }

    public boolean payOrder(Order newOrder) {
        payment.pay(newOrder.payAmount);
        if (payment.pay(newOrder.getPayAmount())) {
            payment.setStatus(PaymentStatus.COMPLETED);
            return true;
        } else {
            return false;
        }

    }
}
