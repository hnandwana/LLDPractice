package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;

public class MacOsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering MacOs Button");
    }

    @Override
    public void click() {
        System.out.println("MacOs Button Clicked");
    }
}
