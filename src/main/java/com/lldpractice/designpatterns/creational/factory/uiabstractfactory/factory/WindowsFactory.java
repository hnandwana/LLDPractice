package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows.WindowsButton;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows.WindowsCheckbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.windows.WindowsTextField;

public class WindowsFactory implements UIComponentFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
