package com.lldpractice.designpatterns.creational.factory.notificationfactory.factory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.EmailNotification;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailNotificationFactory extends NotificationFactory {

    private String recipientEmail;
    private String subject;
    private String body;


    @Override
    public Notification createNotification() {
        if (recipientEmail == null || !recipientEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (body.isBlank() || subject.isBlank()) {
            throw new IllegalArgumentException("Subject and body cannot be empty");
        }
        return new EmailNotification(recipientEmail, subject, body);
    }
}
