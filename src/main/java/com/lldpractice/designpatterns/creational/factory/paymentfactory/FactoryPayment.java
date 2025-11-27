package com.lldpractice.designpatterns.creational.factory.paymentfactory;

import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.Payment;

public class FactoryPayment {

    public static void main(String[] args) {

        PaymentFactory creditCardFactory = new CreditCardPaymentFactory("1234-5678-9012-3456", "12/25", "123");
        Payment creditCardPayment = creditCardFactory.createPayment();
        creditCardPayment.processPayment(500.0);

        PaymentFactory paypalFactory  = new PayPalPaymentFactory("test@gmail.com");
        paypalFactory.processPayment(800.0);



    }
}
