package com.lldpractice.solidprinciples.openclosed.valid;

public class SMSNotification implements Notification {

    @Override
    public void send(String message) {
        // Logic to send SMS notification
        System.out.println("Sending SMS notification: " + message);
    }
}
