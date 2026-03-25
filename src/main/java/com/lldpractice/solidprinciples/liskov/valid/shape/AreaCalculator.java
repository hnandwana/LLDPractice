package com.lldpractice.solidprinciples.liskov.valid.shape;

class AreaCalculator {
    public void printArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());  // ✅ Works for ANY shape
    }
}