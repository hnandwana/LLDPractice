package com.lldpractice.designpatterns.structural.facade;

import com.lldpractice.designpatterns.structural.facade.orderworkflow.OrderFacade;

public class FacadeOrder {

    public static void main(String args[]) {

        OrderFacade orderFacade = new OrderFacade();
        System.out.println("Testing for new order with facade..............");

        // Client must know the EXACT order of operations!
        String customerId = "CUST123";
        String productId = "PROD456";
        int quantity = 2;
        double amount = 99.99;
        String address = "123 Main St, Delhi";

        // Using Facade to place order
        boolean orderSuccess = orderFacade.placeOrder(customerId, productId, quantity, amount, address);
        if (orderSuccess) {
            System.out.println("Order placed successfully via Facade!");
        } else {
            System.out.println("Order placement failed via Facade.");
        }

        //Out of stock scenario
        System.out.println("Testing for out of stock scenario with facade..............");
        customerId = "CUST222";
        productId = "PROD999"; // Assume this product is out of stock
        quantity = 50;
        amount = 199.99;
        address = "789 Pine St, Bangalore";
        orderSuccess = orderFacade.placeOrder(customerId, productId, quantity, amount, address);
        if (orderSuccess) {
            System.out.println("Order placed successfully via Facade!");
        } else {
            System.out.println("Order placement failed via Facade.");
        }


//        Payment failure scenario
        System.out.println("Testing for payment failure scenario with facade..............");
        customerId = "CUST333";
        productId = "PROD202";
        quantity = 1;
        amount = 0.0; // Assume this will cause payment failure
        address = "321 Oak St, Chennai";
        orderSuccess = orderFacade.placeOrder(customerId, productId, quantity, amount, address
        );
        ;
        if (orderSuccess) {
            System.out.println("Order placed successfully via Facade!");
        } else {
            System.out.println("Order placement failed via Facade.");
        }


    }
}