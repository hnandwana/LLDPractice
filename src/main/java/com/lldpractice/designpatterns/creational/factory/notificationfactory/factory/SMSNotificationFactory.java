package com.lldpractice.designpatterns.creational.factory.notificationfactory.factory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.SMSNotification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SMSNotificationFactory extends NotificationFactory {
    private String phoneNumber;
    private String message;

    @Override
    public Notification createNotification() {
        if (phoneNumber == null || message == null) {
            throw new IllegalArgumentException("Phone number and message cannot be null");
        }
        if (message.length() > 160) {
            throw new IllegalArgumentException("Message length cannot exceed 160 characters");
        }
        return new SMSNotification(phoneNumber, message);
    }
}
