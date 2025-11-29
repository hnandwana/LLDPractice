package com.lldpractice.designpatterns.behavioural.strategy.shippingstrategy;

import com.lldpractice.designpatterns.behavioural.strategy.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.model.ShippingResult;

public interface ShippingStrategy {
    ShippingResult calculateShipping(Parcel parcel);
}
