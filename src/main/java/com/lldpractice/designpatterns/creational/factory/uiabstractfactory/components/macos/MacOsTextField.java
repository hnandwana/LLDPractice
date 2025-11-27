package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;

public class MacOsTextField implements TextField {
    @Override
    public void render() {
        System.out.println("Rendering MacOs TextField");
    }

    @Override
    public void inputText() {
        System.out.println("Inputting text in MacOs TextField");
    }
}
