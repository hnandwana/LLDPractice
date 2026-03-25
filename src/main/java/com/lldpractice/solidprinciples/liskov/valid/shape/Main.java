package com.lldpractice.solidprinciples.liskov.valid.shape;

public class Main {
    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();
        calc.printArea(new Rectangle(5, 4));  // ✅ Area: 20
        calc.printArea(new Square(5));         // ✅ Area: 25
        // No broken expectations — each shape correctly computes its own area
    }
}
/*
* Why this works:
Shape contract says: getArea() returns the correct area — both classes honor this
No mutable setters that could break invariants
Objects are immutable — safer and more predictable
Substituting any Shape implementation works correctly
* */

/*
* "LSP means any child class should be safely substitutable for its parent without breaking the code. The classic violation is Rectangle-Square
* Square overrides setters and breaks Rectangle's contract. The fix is to use interfaces and model shared behavior instead of forcing inheritance."
* */