package com.lldpractice.designpatterns.behavioural.strategy.shippingstrategy;

import com.lldpractice.designpatterns.behavioural.strategy.model.OrderStatus;
import com.lldpractice.designpatterns.behavioural.strategy.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.model.ShippingResult;

public class ExpressShippingStrategy implements ShippingStrategy {
    @Override
    public ShippingResult calculateShipping(Parcel parcel) {
        return new ShippingResult("Express Shipping",
                100 + (10 * parcel.getWeight()) + (5 * parcel.getDistance()),
                "1-2 days",
                null,
                OrderStatus.CONFIRMED,
                100,
                10,
                5,
                parcel
        );
    }
}