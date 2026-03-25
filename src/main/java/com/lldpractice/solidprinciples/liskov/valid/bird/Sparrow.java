package com.lldpractice.solidprinciples.liskov.valid.bird;

public class Sparrow implements Bird,Flyable {
    @Override
    public void fly() {
        System.out.println("Sparrow is flying...");
    }

    @Override
    public void eat() {
        System.out.println("Sparrow is eating seeds...");
    }
}
