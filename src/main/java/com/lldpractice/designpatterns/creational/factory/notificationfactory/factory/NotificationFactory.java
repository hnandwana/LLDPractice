package com.lldpractice.designpatterns.creational.factory.notificationfactory.factory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;

public abstract class NotificationFactory {
    public abstract Notification createNotification();

    public void sendNotification() {
        Notification notification = createNotification();
        notification.send();
    }
}
