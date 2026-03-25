package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ New database added — ZERO changes to OrderService
class MongoDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MongoDB: " + data);
    }
}

