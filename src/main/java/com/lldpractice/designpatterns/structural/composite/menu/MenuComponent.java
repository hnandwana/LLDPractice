package com.lldpractice.designpatterns.structural.composite.menu;

public interface MenuComponent {
    double getPrice();

    void display(String indent);

    default void add(MenuComponent component) {
        throw new UnsupportedOperationException("Add operation not supported");
    }

    default void remove(MenuComponent component) {
        throw new UnsupportedOperationException("Remove operation not supported");
    }
}
