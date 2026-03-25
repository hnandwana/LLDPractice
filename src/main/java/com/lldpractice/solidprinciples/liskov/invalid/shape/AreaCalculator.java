package com.lldpractice.solidprinciples.liskov.invalid.shape;

// Client code that works with Rectangle
class AreaCalculator {

    public void printArea(Rectangle rect) {
        rect.setWidth(5);
        rect.setHeight(4);
        // Expectation: area = 5 * 4 = 20
        System.out.println("Expected area: 20, Actual: " + rect.getArea());
    }
}