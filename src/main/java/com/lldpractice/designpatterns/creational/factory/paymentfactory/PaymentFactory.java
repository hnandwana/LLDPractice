package com.lldpractice.designpatterns.creational.factory.paymentfactory;

import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.Payment;

public abstract class PaymentFactory {

    public abstract Payment createPayment();

    public void processPayment(double amount) {
        Payment payment = createPayment();
        payment.processPayment(amount);
    }

}
