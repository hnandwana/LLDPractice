package com.lldpractice.designpatterns.structural.decorator.decor;

import com.lldpractice.designpatterns.structural.decorator.model.Notification;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class NotificationDecorator implements Notification {
    protected Notification notification;
}