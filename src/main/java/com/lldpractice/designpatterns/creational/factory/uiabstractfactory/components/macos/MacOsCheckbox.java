package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;

public class MacOsCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Rendering MacOs Checkbox");
    }

    @Override
    public void toggle() {
        System.out.println("Toggling MacOs Checkbox");
    }
}
