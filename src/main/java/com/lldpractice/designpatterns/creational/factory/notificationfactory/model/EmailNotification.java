package com.lldpractice.designpatterns.creational.factory.notificationfactory.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailNotification extends Notification {

    private String recipientEmail;
    private String subject;
    private String body;

    @Override
    public void send() {

        System.out.println("Sending Email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("Email sent successfully!");
    }
}
