package com.lldpractice.designpatterns.creational.factory.uiabstractfactory;

import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Button;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.Checkbox;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.components.TextField;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory.UIComponentFactory;
import com.lldpractice.designpatterns.creational.factory.uiabstractfactory.factory.WindowsFactory;

public class FactoryUIComponent {


    public static void main(String[] args) {


        UIComponentFactory windowsFactory = new WindowsFactory();

        //first have a variable then call the methods

            Button button = windowsFactory.createButton();
            button.render();
            button.click();
            TextField textField = windowsFactory.createTextField();
            textField.render();
            textField.inputText();
            Checkbox checkbox = windowsFactory.createCheckbox();
            checkbox.render();
            checkbox.toggle();

    }
}
