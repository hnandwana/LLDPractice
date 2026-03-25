package com.lldpractice.solidprinciples.openclosed.valid;

public class NotificationMain {

    public static void main(String[] args) {
        // Create instances of different notification types
        Notification emailNotification = new EmailNotification();
        Notification smsNotification = new SMSNotification();
        Notification pushNotification = new PushNotification();

        // Create notification services for each type
        NotificationService emailService = new NotificationService(emailNotification);
        NotificationService smsService = new NotificationService(smsNotification);
        NotificationService pushService = new NotificationService(pushNotification);

        // Send notifications
        emailService.notifyUser("This is an email notification.");
        smsService.notifyUser("This is an SMS notification.");
        pushService.notifyUser("This is a push notification.");
    }
}
/*
* BEFORE (Violates OCP):                     AFTER (Follows OCP):

NotificationService                        «interface» Notification
├── if EMAIL → send email                       ↑ implements
├── if SMS → send SMS                    ┌───────┼────────┬──────────┐
├── if PUSH → send push                 Email  SMS    Push    Slack ← NEW
├── if SLACK → ❌ MODIFY!               (each class is independent)
└── if WHATSAPP → ❌ MODIFY AGAIN!
                                         NotificationService
                                         └── uses Notification (interface)
                                             → works with ANY implementation
* */

/*
* "OCP means I can add new features by creating new classes without modifying existing tested code.
* I achieve this using interfaces and patterns like Strategy and Factory,
* which is exactly how I built the notification and shipping systems in my projects."
* */