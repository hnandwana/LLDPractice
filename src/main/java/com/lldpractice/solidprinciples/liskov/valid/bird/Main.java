package com.lldpractice.solidprinciples.liskov.valid.bird;

public class Main {

    public static void main(String[] args) {
        Sparrow sparrow = new Sparrow();
        Ostrich ostrich = new Ostrich();
        sparrow.fly();
        sparrow.eat();
        ostrich.eat();
    }
}
