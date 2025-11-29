package com.lldpractice.designpatterns.behavioural.strategy;

import com.lldpractice.designpatterns.behavioural.strategy.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.model.ShippingResult;
import com.lldpractice.designpatterns.behavioural.strategy.service.ShippingCalculator;
import com.lldpractice.designpatterns.behavioural.strategy.shippingstrategy.*;

public class ShipmentDemo {

    public static void main(String[] args) {

        Parcel package1 = new Parcel(5.0, 100, 1500);
        // Test Standard Shipping
        System.out.println("=== Standard Shipping ===");
        ShippingCalculator calculator = new ShippingCalculator(new StandardShippingStrategy());
        ShippingResult result = calculator.calculateShippingCost(package1);
        result.display();

        // Test Express Shipping
        System.out.println("\n=== Express Shipping ===");
        calculator.setShippingStrategy(new ExpressShippingStrategy());
        result = calculator.calculateShippingCost(package1);
        result.display();

        // Test Overnight Shipping
        System.out.println("\n=== Overnight Shipping ===");
        calculator.setShippingStrategy(new OvernightShippingStrategy());
        result = calculator.calculateShippingCost(package1);
        result.display();

        // Test Free Shipping (eligible)
        System.out.println("\n=== Free Shipping (Eligible) ===");
        calculator.setShippingStrategy(new FreeShippingStrategy());
        result = calculator.calculateShippingCost(package1);
        result.display();

        // Test Free Shipping (not eligible - order < ₹1000)
        System.out.println("\n=== Free Shipping (Not Eligible) ===");
        Parcel package2 = new Parcel(3.0, 50, 500);// ₹500 order
        result = calculator.calculateShippingCost(package2);
        result.display();

        // Test Overnight with overweight package (should reject)
        System.out.println("\n=== Overnight Shipping (Overweight) ===");
        Parcel package3 = new Parcel(15.0, 100, 2000);// 15kg (over limit)
        calculator.setShippingStrategy(new OvernightShippingStrategy());
        result = calculator.calculateShippingCost(package3);
        result.display();


        // Compare all options for a package
        System.out.println("\n=== Comparing All Shipping Options ===");
        Parcel package4 = new Parcel(8.0, 150, 2000);
        compareShippingOptions(package4);

    }


    private static void compareShippingOptions(Parcel pkg) {
        ShippingStrategy[] strategies = {
                new StandardShippingStrategy(),
                new ExpressShippingStrategy(),
                new OvernightShippingStrategy(),
                new FreeShippingStrategy()
        };

        ShippingCalculator calculator = new ShippingCalculator(null);

        for (ShippingStrategy strategy : strategies) {
            calculator.setShippingStrategy(strategy);
            ShippingResult result = calculator.calculateShippingCost(pkg);
            System.out.println(result.getCarrierName() + ": ₹" + result.getTotalCost() +
                    " (" + result.getDeliveryTime() + ")");
        }
    }
}
