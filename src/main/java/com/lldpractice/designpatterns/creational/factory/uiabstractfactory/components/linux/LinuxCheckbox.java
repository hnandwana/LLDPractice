package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;

public class LinuxCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Rendering Linux Checkbox");
    }

    @Override
    public void toggle() {
        System.out.println("Toggling Linux Checkbox");
    }
}
