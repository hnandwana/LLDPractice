package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CryptoPayment implements Payment {

    private final String walletAddress;

    @Override
    public void processPayment(double amount) {
        validatePaymentDetails();
        System.out.println("Processing Crypto payment of amount: " + amount);
        System.out.println("Crypto payment successful!");
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to Crypto wallet");
        System.out.println("Refund successful!");
    }

    @Override
    public void validatePaymentDetails() {
        System.out.println("Validating Cryptocurrency details...");

        if (walletAddress == null || walletAddress.isEmpty()) {
            throw new IllegalArgumentException("Wallet address is required");
        }

        System.out.println("Wallet address: " + walletAddress);
        System.out.println("Validation successful!");
    }
}
