package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

public class SimpleFactoryPayment {

    public static void main(String[] args) {
        Payment creditCard = PaymentSimpleFactory.createPayment(PaymentType.CREDIT_CARD,"1234-5678-9012-3456","12/25","123");
        creditCard.processPayment(100.0);
        creditCard.refund(20.0);

        Payment paypal = PaymentSimpleFactory.createPayment(PaymentType.PAYPAL,"test@test.com");
        paypal.processPayment(50.0);

        Payment crypto = PaymentSimpleFactory.createPayment(PaymentType.CRYPTO,"1A2b3C4d5E6f7G8h9I0j");
        crypto.processPayment(200.0);


        Payment creditCard2 = PaymentSimpleFactory.createCreditCardPayment("1234-5678-9012-3456", "123", "12/25");
        creditCard2.processPayment(150.0);

        Payment paypal2 = PaymentSimpleFactory.creditPaypalPayment("test@gmail.com");
        paypal2.processPayment(75.0);

        Payment crypto2 = PaymentSimpleFactory.creditCryptoPayment("9I8h7G6f5E4d3C2b1A0j");
        crypto2.processPayment(300.0);

    }
}
