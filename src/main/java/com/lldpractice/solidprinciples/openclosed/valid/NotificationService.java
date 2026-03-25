package com.lldpractice.solidprinciples.openclosed.valid;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationService {

    private final Notification notification;

    public void notifyUser(String message) {
        notification.send(message);
    }
}
