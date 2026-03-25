package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ ABSTRACTION — high-level and low-level BOTH depend on this
// OrderService depends on THIS, not on MySQLDatabase or MongoDatabase
public interface Database {
    void save(String data);
}

