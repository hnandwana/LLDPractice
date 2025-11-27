package com.lldpractice.designpatterns.structural.decorator.decor;

import com.lldpractice.designpatterns.structural.decorator.model.Notification;

public class CompressionDecorator extends NotificationDecorator {

    public CompressionDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String send() {
        return "[COMPRESSED] " + notification.send();
    }

    @Override
    public double getCost() {
        return notification.getCost() + 0.6;
    }
}
