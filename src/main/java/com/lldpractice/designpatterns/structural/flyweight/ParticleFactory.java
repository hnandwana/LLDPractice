package com.lldpractice.designpatterns.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ParticleFactory {

    private final Map<String, ParticleType> flyweights = new HashMap<>();
    // Statistics tracking
    private int creationCount = 0;
    private int reuseCount = 0;

    public ParticleType getParticleType(String typeName) {
        if(!flyweights.containsKey(typeName))
        {
            flyweights.put(typeName,createParticleType(typeName));
            System.out.println("[Factory] Creating new ParticleType: " + typeName);
            creationCount++;
        }
        else {
            reuseCount++;
        }

        return flyweights.get(typeName);
    }

    private ParticleType createParticleType(String typeName) {
        return switch (typeName.toUpperCase()) {
            case "FIRE" -> new ParticleType("FIRE", "fire_particle.png", "RED", 6);
            case "SMOKE" -> new ParticleType("SMOKE", "smoke_cloud.png", "GRAY", 10);
            case "EXPLOSION" -> new ParticleType("EXPLOSION", "explosion_spark.png", "YELLOW", 8);
            case "RAIN" -> new ParticleType("RAIN", "water_drop.png", "BLUE", 3);
            default -> throw new IllegalArgumentException("Unknown particle type: " + typeName);
        };
    }

    public void printStatistics(int totalParticles) {
        System.out.println("Total Particles: " + totalParticles);
        System.out.println("Unique Particle Types (Flyweights): " + flyweights.size());
        System.out.println("Flyweights Created: " + creationCount + " times");
        System.out.println("Flyweights Reused: " + reuseCount + " times");

        if (!flyweights.isEmpty()) {
            System.out.println("Reuse Factor: " + (totalParticles / flyweights.size()) + "x average per type");
        }

        // Memory calculation
        calculateMemorySavings(totalParticles);
    }

    private void calculateMemorySavings(int totalParticles) {
        System.out.println("\nMemory Analysis:");

        // Assume: Intrinsic state = 100 bytes, Extrinsic state = 50 bytes per particle
        int intrinsicBytes = 100;
        int extrinsicBytes = 50;

        // WITHOUT Flyweight: Each particle stores intrinsic + extrinsic
        long withoutFlyweight = totalParticles * (intrinsicBytes + extrinsicBytes);

        // WITH Flyweight: Few flyweights store intrinsic, each particle stores extrinsic
        long withFlyweight = (flyweights.size() * intrinsicBytes) + (totalParticles * extrinsicBytes);

        long saved = withoutFlyweight - withFlyweight;
        double percentage = (saved * 100.0) / withoutFlyweight;

        System.out.println("WITHOUT Flyweight Pattern:");
        System.out.println("  - " + totalParticles + " particles × " + (intrinsicBytes + extrinsicBytes) +
                " bytes = " + withoutFlyweight + " bytes (~" + (withoutFlyweight / (1024.0 * 1024.0)) + " MB)");

        System.out.println("\nWITH Flyweight Pattern:");
        System.out.println("  - " + flyweights.size() + " flyweights × " + intrinsicBytes +
                " bytes = " + (flyweights.size() * intrinsicBytes) + " bytes");
        System.out.println("  - " + totalParticles + " particles × " + extrinsicBytes +
                " bytes = " + (totalParticles * extrinsicBytes) + " bytes");
        System.out.println("  - Total = " + withFlyweight + " bytes (~" + (withFlyweight / (1024.0 * 1024.0)) + " MB)");

        System.out.println("\nMEMORY SAVED: ~" + (saved / (1024.0 * 1024.0)) + " MB (" +
                String.format("%.1f", percentage) + "% reduction!) 🎉");
    }

}
