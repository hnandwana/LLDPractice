package com.lldpractice.designpatterns.behavioural.strategy.shipping.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShippingResult {
    private String carrierName;
    private double totalCost;
    private String deliveryTime;
    private String reason;
    private OrderStatus status;
    private int baseCost;
    private int weightCost;
    private int distanceCost;
    private Parcel parcel;

    public void display() {
        System.out.println("Carrier: " + carrierName);
        System.out.println("Cost: ₹" + totalCost);
        System.out.println("Delivery Time: " + deliveryTime);
        System.out.println("Status: " + status);
        if (carrierName.equals("Free Shipping") && OrderStatus.CONFIRMED.equals(status)) {
            System.out.println();
            System.out.println("Eligible for free shipping (Order total: ₹" + parcel.getOrderTotal() + ")");
        } else if (carrierName.equals("Free Shipping") && OrderStatus.REJECTED.equals(status)) {
            System.out.println();
            System.out.println("Reason: Order total (₹" + parcel.getOrderTotal() + ") must be at least ₹1000 for free shipping");
        } else if (carrierName.equals("Overnight Shipping") && OrderStatus.REJECTED.equals(status)) {
            System.out.println();
            System.out.println("Reason: Package weight (" + parcel.getWeight() + "kg) exceeds the limit for this shipping method");
        } else {
            System.out.println();
            System.out.println("Calculation: " +
                    "Base(₹" + baseCost + ") + " +
                    "Weight(" + parcel.getWeight() + "kg × ₹" + (weightCost) + ") + " +
                    "Distance(" + parcel.getDistance() + "km × ₹" + (distanceCost) + ") = ₹" +
                    totalCost);
        }
    }
}
