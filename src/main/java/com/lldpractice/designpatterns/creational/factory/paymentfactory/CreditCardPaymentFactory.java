package com.lldpractice.designpatterns.creational.factory.paymentfactory;

import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.CreditCardPayment;
import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.Payment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditCardPaymentFactory extends PaymentFactory {
    private final String cardNumber;
    private final String expiryDate;
    private final String cvv;

    @Override
    public Payment createPayment() {
        return new CreditCardPayment(cardNumber, expiryDate, cvv);
    }
}
