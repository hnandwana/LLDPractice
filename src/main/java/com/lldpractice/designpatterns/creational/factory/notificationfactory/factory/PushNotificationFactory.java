package com.lldpractice.designpatterns.creational.factory.notificationfactory.factory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.PushNotification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PushNotificationFactory extends NotificationFactory {
    private String deviceToken;
    private String title;
    private String message;

    @Override
    public Notification createNotification() {
        if (title.isBlank() || message.isBlank() || deviceToken.isBlank()) {
            throw new IllegalArgumentException("Title, message and device token cannot be empty");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Title cannot exceed 50 characters");
        }
        return new PushNotification(deviceToken, title, message);
    }
}
