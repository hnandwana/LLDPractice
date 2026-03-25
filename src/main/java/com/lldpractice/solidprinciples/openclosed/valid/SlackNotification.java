package com.lldpractice.solidprinciples.openclosed.valid;

// ✅ NEW class — no existing code modified
class SlackNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SLACK: " + message);
        // Slack API logic here
    }
}