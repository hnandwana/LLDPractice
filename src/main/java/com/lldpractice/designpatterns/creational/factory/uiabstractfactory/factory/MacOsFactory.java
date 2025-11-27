package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos.MacOsButton;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos.MacOsCheckbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.macos.MacOsTextField;

public class MacOsFactory implements UIComponentFactory {
    @Override
    public Button createButton() {
        return new MacOsButton();
    }

    @Override
    public TextField createTextField() {
        return new MacOsTextField();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOsCheckbox();
    }
}
