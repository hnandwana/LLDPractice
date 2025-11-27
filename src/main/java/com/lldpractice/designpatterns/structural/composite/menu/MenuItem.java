package com.lldpractice.designpatterns.structural.composite.menu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MenuItem implements MenuComponent {

    private String name;
    private double price;

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name + ": ₹" + price);
    }
}
