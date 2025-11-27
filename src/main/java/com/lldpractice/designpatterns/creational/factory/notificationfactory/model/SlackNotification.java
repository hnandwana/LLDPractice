package com.lldpractice.designpatterns.creational.factory.notificationfactory.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlackNotification extends Notification {

    private String channelName;
    private String message;

    @Override
    public void send() {
        System.out.println("Sending Slack notification to channel: " + channelName);
        System.out.println("Message: " + message);
        System.out.println("Slack notification sent successfully!");
    }
}
