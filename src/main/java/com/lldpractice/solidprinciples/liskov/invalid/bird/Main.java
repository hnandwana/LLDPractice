package com.lldpractice.solidprinciples.liskov.invalid.bird;

public class Main {

    public static void main(String[] args) {
        Fly fly = new Fly();

        Bird sparrow = new Bird();
        fly.makeBirdFly(sparrow);  // Works fine

        Bird ostrich = new Ostrich();
        fly.makeBirdFly(ostrich);  // Crashes with UnsupportedOperationException!
    }
}
