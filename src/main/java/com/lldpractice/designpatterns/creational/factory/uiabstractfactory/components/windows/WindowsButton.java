package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;

public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Windows Button");
    }

    @Override
    public void click() {
        System.out.println("Windows Button Clicked");
    }
}
