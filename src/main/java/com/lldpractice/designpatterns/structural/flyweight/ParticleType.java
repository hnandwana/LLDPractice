package com.lldpractice.designpatterns.structural.flyweight;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParticleType implements ParticleFlyweight {

    private final String name;
    private final String texture;
    private final String color;
    private final int size;

    @Override
    public void render(int x, int y, double velocity, int lifespan) {
        System.out.println("[" + name + "] Rendering at (" + x + ", " + y + ") | " + "Texture: " + texture + " | " + "Color: " + color + " | " + "Size: " + size + "px |" +
                " Velocity: " + velocity + " | " + "Lifespan: " + lifespan + " frames");
    }
}