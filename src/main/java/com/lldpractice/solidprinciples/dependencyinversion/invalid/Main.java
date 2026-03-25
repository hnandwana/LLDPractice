package com.lldpractice.solidprinciples.dependencyinversion.invalid;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        service.placeOrder("Laptop");
        // ✅ Works — but completely inflexible

        // ❌ Want to use MongoDB instead of MySQL?
        //    → Must go INSIDE OrderService and change the field. Violates OCP too.

        // ❌ Want to use SMS instead of Email?
        //    → Must go INSIDE OrderService and change the field.

        // ❌ Want to write a unit test without a real DB?
        //    → IMPOSSIBLE — you can't inject a mock. OrderService creates its own dependencies.
    }
}

/*
 * What's wrong?
 * OrderService (high-level) directly creates its own low-level dependencies.
 * It's tightly coupled to MySQLDatabase and EmailSender — specific concrete classes.
 * To switch DB or notification, you MUST modify OrderService business logic.
 * Unit testing is impossible — can't inject mocks.
 *
 * DIP Violation: High-level module depends on low-level module (concrete class).
 * Fix: Both should depend on ABSTRACTIONS (interfaces).
 */

