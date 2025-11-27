package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreditCardPayment implements Payment {

    private final String cardNumber;

    private final String expiryDate;

    private final String cvv;
    @Override
    public void processPayment(double amount) {
        validatePaymentDetails();
        System.out.println("Processing Credit Card payment of amount: " + amount);
        System.out.println("Credit Card payment successful!");
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to Credit Card");
        System.out.println("Refund successful!");
    }

    @Override
    public void validatePaymentDetails() {

        System.out.println("Validating Credit Card details...");

        // Actual validation
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number is required");
        }
        if (cvv == null || cvv.length() != 3) {
            throw new IllegalArgumentException("CVV must be 3 digits");
        }
        if (expiryDate == null || expiryDate.isEmpty()) {
            throw new IllegalArgumentException("Expiry date is required");
        }

        System.out.println("Card number: " + maskCardNumber(cardNumber));
        System.out.println("Expiry date: " + expiryDate);
        System.out.println("Validation successful!");


    }

    public static String maskCardNumber(String cardNumber) {
        String clean = cardNumber.replaceAll("[\\s-]", "");
        return clean.length() > 4
                ? "*".repeat(clean.length() - 4) + clean.substring(clean.length() - 4)
                : clean;
    }
}
