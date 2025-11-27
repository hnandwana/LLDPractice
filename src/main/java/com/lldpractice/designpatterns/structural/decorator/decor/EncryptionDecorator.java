package com.lldpractice.designpatterns.structural.decorator.decor;

import com.lldpractice.designpatterns.structural.decorator.model.Notification;

public class EncryptionDecorator extends NotificationDecorator {

    public EncryptionDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String send() {
        String message = notification.send();
        String encryptedMessage = java.util.Base64.getEncoder().encodeToString(message.getBytes());
        return "[ENCRYPTED:" + encryptedMessage + "]";
    }

    @Override
    public double getCost() {
        return notification.getCost()+ 0.5;
    }
}
