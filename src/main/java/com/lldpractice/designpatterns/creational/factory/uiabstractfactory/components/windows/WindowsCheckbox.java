package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;

public class WindowsCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Rendering Windows Checkbox");
    }

    @Override
    public void toggle() {
        System.out.println("Toggling Windows Checkbox");
    }
}
