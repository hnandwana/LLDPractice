package com.lldpractice.designpatterns.creational.factory.notificationfactory.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PushNotification extends Notification {

    private String deviceToken;
    private String title;
    private String message;

    @Override
    public void send() {
        System.out.println("Sending Push Notification to device token: " + deviceToken);
        System.out.println("Title: " + title);
        System.out.println("Message: " + message);
        System.out.println("Push Notification sent successfully!");
    }
}
