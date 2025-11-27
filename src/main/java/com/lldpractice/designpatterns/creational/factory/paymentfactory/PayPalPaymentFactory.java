package com.lldpractice.designpatterns.creational.factory.paymentfactory;

import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.PayPalPayment;
import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.Payment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PayPalPaymentFactory extends PaymentFactory {
    private final String email;

    @Override
    public Payment createPayment() {
        return new PayPalPayment(email);
    }
}
