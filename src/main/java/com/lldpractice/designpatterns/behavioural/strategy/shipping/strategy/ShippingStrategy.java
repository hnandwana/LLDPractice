package com.lldpractice.designpatterns.behavioural.strategy.shipping.strategy;

import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.ShippingResult;

public interface ShippingStrategy {
    ShippingResult calculateShipping(Parcel parcel);
}
