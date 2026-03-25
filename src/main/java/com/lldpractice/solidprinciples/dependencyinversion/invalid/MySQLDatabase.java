package com.lldpractice.solidprinciples.dependencyinversion.invalid;

// Low-level class — concrete implementation (MySQL specific)
class MySQLDatabase {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

