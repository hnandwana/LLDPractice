package com.lldpractice.solidprinciples.openclosed.invalid;

public class NotificationService {

    public void sendNotification(String type, String message) {
        if (type.equals("EMAIL")) {
            // Send email
            System.out.println("Sending EMAIL: " + message);
            // ... SMTP logic
        } else if (type.equals("SMS")) {
            // Send SMS
            System.out.println("Sending SMS: " + message);
            // ... Twilio logic
        } else if (type.equals("PUSH")) {
            // Send Push notification
            System.out.println("Sending PUSH: " + message);
            // ... Firebase logic
        }
        // ❌ To add SLACK notification, you MUST modify this class
        // ❌ To add WHATSAPP, modify again
        // ❌ Every new type = modify existing tested code
    }
}

/*
* What's wrong?
Every new notification type requires modifying NotificationService
The if-else chain keeps growing
Risk of breaking existing EMAIL/SMS logic when adding SLACK
Violates OCP — class is NOT closed for modification
* */

/*
* How to Spot OCP Violations
if-else / switch on type ->	New type = modify existing code
instanceof checks ->	Fragile type checking
Adding a feature requires editing 5 files ->	Tight coupling
"I just added X and Y broke" ->	Modification side-effects
* */