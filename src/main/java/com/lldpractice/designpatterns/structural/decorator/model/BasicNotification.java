package com.lldpractice.designpatterns.structural.decorator.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasicNotification implements Notification{

    private String message;

    @Override
    public String send() {
        return message;
    }

    @Override
    public double getCost() {
        return 0.0;
    }
}
