package com.lldpractice.designpatterns.structural.facade.subsystem;

import java.time.LocalDateTime;

public class ShippingService {
    public String scheduleShipping(String productId, String address, int quantity) {
        System.out.println("Scheduling shipping for " + quantity + " units to: " + address);
        return "SHIP-" + LocalDateTime.now()+" "+ productId; // Tracking number
    }
    
    public void cancelShipping(String trackingNumber) {
        System.out.println("Cancelled shipping: " + trackingNumber);
    }
}
