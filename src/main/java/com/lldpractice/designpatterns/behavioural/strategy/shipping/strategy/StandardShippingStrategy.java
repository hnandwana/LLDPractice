package com.lldpractice.designpatterns.behavioural.strategy.shipping.strategy;

import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.OrderStatus;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.ShippingResult;

public class StandardShippingStrategy implements ShippingStrategy {

    @Override
    public ShippingResult calculateShipping(Parcel parcel) {
        return new ShippingResult("Standard Shipping",
                50 + (5 * parcel.getWeight()) + (2 * parcel.getDistance()),
                "5-7 days",
                null,
                OrderStatus.CONFIRMED,
                50,
                5,
                2,
                parcel
        );
    }
}
