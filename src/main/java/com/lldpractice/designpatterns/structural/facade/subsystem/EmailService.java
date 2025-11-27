package com.lldpractice.designpatterns.structural.facade.subsystem;


public class EmailService {
    public void sendConfirmationEmail(String customerId, String orderId) {
        System.out.println("Sending confirmation email to customer: " + customerId);
        System.out.println("Order ID: " + orderId);
    }
    
    public void sendCancellationEmail(String customerId, String reason) {
        System.out.println("Sending cancellation email to customer: " + customerId);
        System.out.println("Reason: " + reason);
    }
}

