package com.lldpractice.designpatterns.structural.decorator.decor;

import com.lldpractice.designpatterns.structural.decorator.model.Notification;

public class TimestampDecorator extends NotificationDecorator {

    public TimestampDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String send() {

        //Timestamp - Add timestamp to message
        return "[TIMESTAMP: " + System.currentTimeMillis() + "] " + notification.send();
    }

    @Override
    public double getCost() {
        return notification.getCost() + 0.3;
    }
}
