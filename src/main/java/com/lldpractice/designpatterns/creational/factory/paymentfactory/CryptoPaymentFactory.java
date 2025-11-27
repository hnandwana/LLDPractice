package com.lldpractice.designpatterns.creational.factory.paymentfactory;

import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.CryptoPayment;
import com.lldpractice.designpatterns.creational.factory.paymentsimplefactory.Payment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CryptoPaymentFactory extends PaymentFactory {
    private final String walletAddress;

    @Override
    public Payment createPayment() {
        return new CryptoPayment(walletAddress);
    }
}
