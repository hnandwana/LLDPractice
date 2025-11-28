package com.lldpractice.designpatterns.structural.flyweight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Particle {
    private final ParticleFlyweight type;
    private final int x;
    private final int y;
    private final double velocity;
    private final int lifespan;

    public void render() {
        type.render(x, y, velocity, lifespan);
    }
}
