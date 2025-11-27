package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux.LinuxButton;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux.LinuxCheckbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.linux.LinuxTextField;

public class LinuxFactory implements UIComponentFactory{
    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public TextField createTextField() {
        return new LinuxTextField();
    }

    @Override
    public Checkbox createCheckbox() {
        return new LinuxCheckbox();
    }


}
