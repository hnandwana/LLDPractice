package com.lldpractice.designpatterns.structural.facade.subsystem;

public class PaymentService {
    public boolean processPayment(String customerId, double amount) {
        System.out.println("Processing payment of $" + amount + " for customer: " + customerId);
        // Simulate payment processing
        return amount > 0 && amount < 10000; // Payments under $10k succeed
    }
    
    public void refund(String customerId, double amount) {
        System.out.println("Refunding $" + amount + " to customer: " + customerId);
    }
}
