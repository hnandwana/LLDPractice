package com.lldpractice.solidprinciples.liskov.valid.bird;

public class Ostrich implements Bird {
    @Override
    public void eat() {
        System.out.println("Ostrich is eating seeds.");
    }
}
