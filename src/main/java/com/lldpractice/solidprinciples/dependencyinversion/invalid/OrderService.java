package com.lldpractice.solidprinciples.dependencyinversion.invalid;

// ❌ HIGH-LEVEL class — directly depends on LOW-LEVEL concrete classes
// OrderService is the important business logic class
// It should NOT know WHICH database or WHICH email provider is being used
class OrderService {

    // ❌ Hardcoded — tightly coupled to MySQL specifically
    private MySQLDatabase database = new MySQLDatabase();

    // ❌ Hardcoded — tightly coupled to Email specifically
    private EmailSender emailSender = new EmailSender();

    public void placeOrder(String order) {
        database.save(order);         // ❌ Only works with MySQL. Want MongoDB? Modify this class.
        emailSender.sendEmail(order); // ❌ Only works with Email. Want SMS? Modify this class.
        System.out.println("Order placed: " + order);
    }
}

