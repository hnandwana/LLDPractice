package com.lldpractice.designpatterns.structural.decorator.decor;

import com.lldpractice.designpatterns.structural.decorator.model.Notification;

public class ReadReceiptDecorator extends NotificationDecorator {

    public ReadReceiptDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String send() {
        return "[READ_RECEIPT_ENABLED] " + notification.send();
    }

    @Override
    public double getCost() {
        return notification.getCost() + 0.7;
    }
}
