package com.lldpractice.solidprinciples.openclosed.valid;

public class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        // Logic to send email notification
        System.out.println("Sending email notification: " + message);
    }

}
