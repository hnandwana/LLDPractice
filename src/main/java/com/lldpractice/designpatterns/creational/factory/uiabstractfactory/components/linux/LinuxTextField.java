package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;

public class LinuxTextField implements TextField {
    @Override
    public void render() {
        System.out.println("Rendering Linux TextField");
    }

    @Override
    public void inputText() {
        System.out.println("Inputting text in Linux TextField");
    }
}
