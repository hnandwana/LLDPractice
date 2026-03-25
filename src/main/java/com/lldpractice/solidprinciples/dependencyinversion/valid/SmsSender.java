package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ New notifier added — ZERO changes to OrderService
class SmsSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

