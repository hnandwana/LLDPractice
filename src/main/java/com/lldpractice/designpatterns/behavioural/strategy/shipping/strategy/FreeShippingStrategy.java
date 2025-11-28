package com.lldpractice.designpatterns.behavioural.strategy.shipping.strategy;

import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.OrderStatus;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.ShippingResult;

public class FreeShippingStrategy implements ShippingStrategy {
    @Override
    public ShippingResult calculateShipping(Parcel parcel) {
        if (parcel.getOrderTotal() <= 1000) {
            return new ShippingResult("Free Shipping",
                    0.0,
                    "N/A",
                    null,
                    OrderStatus.REJECTED,
                    0,
                    0,
                    0,
                    parcel
            );
        }
        return new ShippingResult("Free Shipping",
                0.0,
                "7-10 days",
                null,
                OrderStatus.CONFIRMED,
                0,
                0,
                0,
                parcel
        );
    }
}
