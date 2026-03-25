package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ Low-level class — implements the NotificationSender abstraction
class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

