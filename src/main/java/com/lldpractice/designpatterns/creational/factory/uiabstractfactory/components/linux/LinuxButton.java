package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;

public class LinuxButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Linux Button");
    }

    @Override
    public void click() {
        System.out.println("Linux Button Clicked");
    }
}
