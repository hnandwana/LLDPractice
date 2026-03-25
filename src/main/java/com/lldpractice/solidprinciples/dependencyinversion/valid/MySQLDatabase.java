package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ Low-level class — implements the abstraction (Database interface)
// Details depend on abstraction, NOT the other way around
class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

