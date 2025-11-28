package com.lldpractice.designpatterns.behavioural.strategy.shipping.strategy;

import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.OrderStatus;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.ShippingResult;

public class OvernightShippingStrategy implements ShippingStrategy {

    @Override
    public ShippingResult calculateShipping(Parcel parcel) {
        if (parcel.getWeight() > 10) {
            return new ShippingResult("Overnight Shipping",
                    0,
                    "N/A",
                    null,
                    OrderStatus.REJECTED,
                    200,
                    15,
                    8,
                    parcel
            );
        }
        return new ShippingResult("Overnight Shipping",
                200 + (15 * parcel.getWeight()) + (8 * parcel.getDistance()),
                "Within 24 hours",
                null,
                OrderStatus.CONFIRMED,
                200,
                15,
                8,
                parcel
        );
    }
}
