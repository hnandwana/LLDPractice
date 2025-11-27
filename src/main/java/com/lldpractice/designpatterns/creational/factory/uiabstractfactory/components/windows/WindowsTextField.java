package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;

public class WindowsTextField implements TextField {
    @Override
    public void render() {
        System.out.println("Rendering Windows TextField");
    }

    @Override
    public void inputText() {
        System.out.println("Inputting text in Windows TextField");
    }
}
