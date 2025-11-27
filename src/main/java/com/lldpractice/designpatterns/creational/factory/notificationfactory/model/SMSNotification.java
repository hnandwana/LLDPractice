package com.lldpractice.designpatterns.creational.factory.notificationfactory.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SMSNotification extends Notification {

    private String phoneNumber;
    private String message;

    @Override
    public void send() {
        System.out.println("Sending SMS to " + phoneNumber + " with message: " + message);
    }
}
