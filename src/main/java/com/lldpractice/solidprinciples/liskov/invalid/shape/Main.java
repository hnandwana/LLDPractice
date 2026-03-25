package com.lldpractice.solidprinciples.liskov.invalid.shape;

public class Main {
    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();

        calc.printArea(new Rectangle());  // ✅ Output: Expected area: 20, Actual: 20
        calc.printArea(new Square());     // ❌ Output: Expected area: 20, Actual: 16
        //                                   Square set height=4, which also set width=4 → 4*4=16
    }
}

/*
* What went wrong?
Rectangle contract says: setWidth changes ONLY width, setHeight changes ONLY height
Square breaks this contract — setting height also changes width
Substituting Square where Rectangle is expected produces wrong results
The child changed the behaviour the parent promised → LSP violated
* */