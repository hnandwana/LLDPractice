package com.lldpractice.solidprinciples.singleresponsibility.valid;

// Responsibility 5: Only handles notifications
class EmailService {
    public void sendEmail(String to, String content) {
        // SMTP logic
        System.out.println("Sending email to " + to + ": " + content);
    }
}