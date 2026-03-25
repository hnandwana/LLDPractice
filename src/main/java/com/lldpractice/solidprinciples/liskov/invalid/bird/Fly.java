package com.lldpractice.solidprinciples.liskov.invalid.bird;

public class Fly {
    // Client expects ALL birds to fly
    public void makeBirdFly(Bird bird) {
        bird.fly();  // 💥 Crashes when Ostrich is passed!
    }
}
