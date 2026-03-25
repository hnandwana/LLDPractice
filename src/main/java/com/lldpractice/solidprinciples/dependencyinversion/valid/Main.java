package com.lldpractice.solidprinciples.dependencyinversion.valid;

public class Main {
    public static void main(String[] args) {

        // ✅ Wiring 1: MySQL + Email — pass concrete classes that implement the interfaces
        OrderService service1 = new OrderService(new MySQLDatabase(), new EmailSender());
        service1.placeOrder("Laptop");
        // Output: Saving to MySQL: Laptop
        //         Sending email: Laptop
        //         Order placed: Laptop

        System.out.println("---");

        // ✅ Wiring 2: Switch to MongoDB + SMS — ZERO changes to OrderService
        OrderService service2 = new OrderService(new MongoDatabase(), new SmsSender());
        service2.placeOrder("Phone");
        // Output: Saving to MongoDB: Phone
        //         Sending SMS: Phone
        //         Order placed: Phone

        System.out.println("---");

        // ✅ Wiring 3: Mix and match freely
        OrderService service3 = new OrderService(new MongoDatabase(), new EmailSender());
        service3.placeOrder("Headphones");
    }
}

/*
 * Why this works:
 * OrderService never changes — it only depends on Database and NotificationSender interfaces.
 * To switch from MySQL to MongoDB: just pass new MongoDatabase() — that's it.
 * To switch from Email to SMS: just pass new SmsSender() — that's it.
 * To unit test: pass a MockDatabase and MockNotifier — no real DB or email needed.
 *
 * Key insight:
 * DIP (the principle)   → OrderService depends on interfaces, not concrete classes
 * DI  (the technique)   → interfaces are PASSED IN via constructor, not created inside
 * Both work together to make code loosely coupled, testable, and swappable.
 */

/*
 * "DIP means high-level business logic shouldn't depend on low-level details.
 *  Both should depend on interfaces. In my OrderService, I depend on Database
 *  and NotificationSender interfaces — so I can swap MySQL for MongoDB or
 *  Email for SMS by just changing what I pass in, without touching business logic."
 */

