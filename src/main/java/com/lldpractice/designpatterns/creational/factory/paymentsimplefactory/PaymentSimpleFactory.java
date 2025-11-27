package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

public class PaymentSimpleFactory {

    public static Payment createPayment(PaymentType type, String... paymentDetails) {
        if (type == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        return switch (type) {
            case CREDIT_CARD -> {
                if (paymentDetails.length < 3) {
                    throw new IllegalArgumentException("Credit Card requires 3 parameters: cardNumber, cvv, expiryDate");
                }
                yield new CreditCardPayment(paymentDetails[0], paymentDetails[1], paymentDetails[2]);
            }
            case PAYPAL -> {
                if (paymentDetails.length < 1) {
                    throw new IllegalArgumentException("Paypal requires 1 parameters");
                }
                yield new PayPalPayment(paymentDetails[0]);
            }
            case CRYPTO -> {
                if (paymentDetails.length < 1) {
                    throw new IllegalArgumentException("Crypto requires 1 parameters");
                }
                yield new CryptoPayment(paymentDetails[0]);
            }
        };
    }


    public static Payment createCreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        return new CreditCardPayment(cardNumber, expiryDate, cvv);
    }

    public static Payment creditCryptoPayment(String walletAddress) {
        return new CryptoPayment(walletAddress);
    }

    public static Payment creditPaypalPayment(String email) {
        return new PayPalPayment(email);
    }
}