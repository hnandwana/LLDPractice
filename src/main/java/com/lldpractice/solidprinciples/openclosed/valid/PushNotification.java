package com.lldpractice.solidprinciples.openclosed.valid;

public class PushNotification implements Notification {

    @Override
    public void send(String message) {
        // Logic to send push notification
        System.out.println("Sending push notification: " + message);
    }
}
