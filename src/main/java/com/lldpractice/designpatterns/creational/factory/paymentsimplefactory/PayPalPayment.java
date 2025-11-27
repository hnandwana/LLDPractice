package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PayPalPayment implements Payment {

    private final String email;

    @Override
    public void processPayment(double amount) {
        validatePaymentDetails();
        System.out.println("Processing PayPal payment of amount: " + amount);
        System.out.println("PayPal payment successful!");
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to PayPal account");
        System.out.println("Refund successful!");
    }

    @Override
    public void validatePaymentDetails() {
        System.out.println("Validating PayPal details...");

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }

        System.out.println("PayPal email: " + email);
        System.out.println("Validation successful!");
    }
}
