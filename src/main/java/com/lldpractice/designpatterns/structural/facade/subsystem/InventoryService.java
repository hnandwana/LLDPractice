package com.lldpractice.designpatterns.structural.facade.subsystem;

public class InventoryService {
    public boolean checkStock(String productId, int quantity) {
        System.out.println("Checking stock for product: " + productId);
        // Simulate stock check
        return quantity <= 10; // Assume max 10 items available
    }
    
    public void reserveStock(String productId, int quantity) {
        System.out.println("Reserved " + quantity + " units of " + productId);
    }
    
    public void updateStock(String productId, int quantity) {
        System.out.println("Updated inventory: " + productId + " reduced by " + quantity);
    }
}
