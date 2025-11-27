package com.lldpractice.designpatterns.creational.factory.notificationfactory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.factory.EmailNotificationFactory;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.factory.SMSNotificationFactory;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;

public class FactoryNotification {

    public static void main(String[] args) {
        Notification email = new EmailNotificationFactory("abc@xyz.com", "Welcome to our service!", "Thank you for signing up.").createNotification();
        email.send();



        Notification sms = new SMSNotificationFactory("+1234567890", "Your OTP is 123456").createNotification();
        sms.send();
    }

}