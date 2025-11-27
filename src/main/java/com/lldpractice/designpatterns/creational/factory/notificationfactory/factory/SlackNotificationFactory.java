package com.lldpractice.designpatterns.creational.factory.notificationfactory.factory;

import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.Notification;
import com.lldpractice.designpatterns.creational.factory.notificationfactory.model.SlackNotification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlackNotificationFactory extends NotificationFactory {

    private String channelName;
    private String message;

    @Override
    public Notification createNotification() {


        if (!channelName.startsWith("#")) {
            throw new IllegalArgumentException("Slack channel name must start with '#'");
        }
        return new SlackNotification(channelName, message);
    }
}
