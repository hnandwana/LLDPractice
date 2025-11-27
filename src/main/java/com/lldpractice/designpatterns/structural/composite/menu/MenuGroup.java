package com.lldpractice.designpatterns.structural.composite.menu;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MenuGroup implements MenuComponent {

    private final String name;
    private final GroupType groupType;
    private List<MenuComponent> children = new ArrayList<>();

    @Override
    public double getPrice() {
        return children.stream().mapToDouble(MenuComponent::getPrice).sum();
    }

    @Override
    public void display(String indent) {
        String icon = groupType == GroupType.COMBO ? "📦" :
                groupType == GroupType.CATEGORY ? "📁" : "🎉";
        System.out.println(indent + icon + " " + name + ": ₹" + getPrice());
        children.forEach(component -> component.display(indent + "  "));
    }

    @Override
    public void add(MenuComponent component) {
        children.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        children.remove(component);
    }
}
