package com.lldpractice.designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    public static void main(String[] args) {
        ParticleFactory factory = new ParticleFactory();
        List<Particle> particles = new ArrayList<>();

        // Create explosion particles
        System.out.println("=== Creating Explosion Effect (10,000 particles) ===");
        for (int i = 0; i < 10000; i++) {
            ParticleType explosionType = factory.getParticleType("EXPLOSION");
            Particle particle = new Particle(explosionType,
                    randomX(), randomY(),
                    randomVelocity(),
                    randomLifespan());
            particles.add(particle);
        }

        // Print summary after each batch (not every iteration)
        System.out.println("[Factory] Reusing existing ParticleType: EXPLOSION (reused 9,999 times)\n");

        // Create fire particles
        System.out.println("=== Creating Fire Effect (20,000 particles) ===");
        for (int i = 0; i < 20000; i++) {
            ParticleType fireType = factory.getParticleType("FIRE");
            Particle particle = new Particle(fireType,
                    randomX(), randomY(),
                    randomVelocity(),
                    randomLifespan());
            particles.add(particle);
        }
        System.out.println("[Factory] Reusing existing ParticleType: FIRE (reused 19,999 times)\n");

        // Similar for SMOKE and RAIN...
        // Create smoke particles
        System.out.println("=== Creating Smoke Effect (20,000 particles) ===");
        for (int i = 0; i < 20000; i++) {
            ParticleType fireType = factory.getParticleType("SMOKE");
            Particle particle = new Particle(fireType,
                    randomX(), randomY(),
                    randomVelocity(),
                    randomLifespan());
            particles.add(particle);
        }

        // Render sample particles
        System.out.println("=== Rendering Sample Particles ===");
        for (int i = 0; i < 5; i++) {
            particles.get(i).render();
        }

        // Statistics
        System.out.println("\n=== Memory Optimization Statistics ===");
        factory.printStatistics(particles.size());
    }

    private static int randomX() { return (int)(Math.random() * 1920); }
    private static int randomY() { return (int)(Math.random() * 1080); }
    private static double randomVelocity() { return Math.random() * 10; }
    private static int randomLifespan() { return (int)(Math.random() * 100); }
}
