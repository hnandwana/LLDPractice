package com.lldpractice.designpatterns.creational.factory.paymentsimplefactory;

public interface Payment {

    void processPayment(double amount);

    void refund(double amount);

    void validatePaymentDetails();

}
