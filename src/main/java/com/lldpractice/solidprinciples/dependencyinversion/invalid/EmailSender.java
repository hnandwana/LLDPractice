package com.lldpractice.solidprinciples.dependencyinversion.invalid;

// Low-level class — concrete implementation (Email specific)
class EmailSender {
    public void sendEmail(String message) {
        System.out.println("Sending email: " + message);
    }
}

