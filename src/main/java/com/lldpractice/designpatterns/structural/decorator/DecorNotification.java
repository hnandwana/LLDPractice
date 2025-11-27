package com.lldpractice.designpatterns.structural.decorator;

import com.lldpractice.designpatterns.structural.decorator.decor.EncryptionDecorator;
import com.lldpractice.designpatterns.structural.decorator.decor.TimestampDecorator;
import com.lldpractice.designpatterns.structural.decorator.model.BasicNotification;
import com.lldpractice.designpatterns.structural.decorator.model.Notification;

public class DecorNotification {
    public static void main(String[] args)
    {
//        Notification notification = new BasicNotification("Hello, this is a basic notification.");
//        System.out.println(notification.send());
//        System.out.println(notification.getCost());
//        notification = new EncryptionDecorator(notification);
//        System.out.println(notification.send());
//        System.out.println(notification.getCost());
//        notification = new TimestampDecorator(notification);
//        System.out.println(notification.send());
//        System.out.println(notification.getCost());
//        notification = new ReadReceiptDecorator(notification);
//        System.out.println(notification.send());
//        System.out.println(notification.getCost());
//        notification = new CompressionDecorator(notification);
//        System.out.println(notification.send());
//        System.out.println(notification.getCost());
// Test 1: Your current order
        Notification n1 = new BasicNotification("Secret Message");
        n1 = new EncryptionDecorator(n1);
        n1 = new TimestampDecorator(n1);
        System.out.println("Order 1: " + n1.send());

// Test 2: Reversed order
        Notification n2 = new BasicNotification("Secret Message");
        n2 = new TimestampDecorator(n2);
        n2 = new EncryptionDecorator(n2);
        System.out.println("Order 2: " + n2.send());

//        // Scenario 1: Just encryption (secure message)
//        Notification secure = new EncryptionDecorator(new BasicNotification("Secret"));
//        System.out.println(secure.send());
//        System.out.println(secure.getCost());
//// Scenario 2: Timestamp + Compression (logged message)
//        Notification logged = new CompressionDecorator(
//                new TimestampDecorator(new BasicNotification("Log entry"))
//        );
//        System.out.println(logged.send());
//        System.out.println(logged.getCost());
//// Scenario 3: All features (premium user)
//        Notification premium = new ReadReceiptDecorator(
//                new CompressionDecorator(
//                        new TimestampDecorator(
//                                new EncryptionDecorator(new BasicNotification("VIP message"))
//                        )
//                )
//        );
//        System.out.println(premium.send());
//        System.out.println(premium.getCost());
//
//

    }
}
