package com.lldpractice.solidprinciples.liskov.invalid.bird;

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");  // ❌ LSP VIOLATION
    }
}