package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ HIGH-LEVEL class — depends ONLY on abstractions (interfaces)
// OrderService has NO IDEA whether it's MySQL or Mongo, Email or SMS
// It just knows: "I have something that can save, and something that can send"
class OrderService {

    private final Database database;           // ✅ Interface — not MySQLDatabase
    private final NotificationSender notifier; // ✅ Interface — not EmailSender

    // ✅ DEPENDENCY INJECTION via constructor — dependencies come FROM OUTSIDE
    // This is how DIP is IMPLEMENTED using the DI technique
    public OrderService(Database database, NotificationSender notifier) {
        this.database = database;
        this.notifier = notifier;
    }

    public void placeOrder(String order) {
        database.save(order);   // ✅ Works with MySQL, Mongo, InMemory, or any Database
        notifier.send(order);   // ✅ Works with Email, SMS, Push, or any NotificationSender
        System.out.println("Order placed: " + order);
    }
}

