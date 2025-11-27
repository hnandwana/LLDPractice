package com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;

public interface UIComponentFactory {

    Button createButton();

    TextField createTextField();

    Checkbox createCheckbox();

}
