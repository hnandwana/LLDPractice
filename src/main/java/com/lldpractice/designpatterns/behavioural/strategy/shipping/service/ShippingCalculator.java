package com.lldpractice.designpatterns.behavioural.strategy.shipping.service;

import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.model.ShippingResult;
import com.lldpractice.designpatterns.behavioural.strategy.shipping.strategy.ShippingStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShippingCalculator {

    private ShippingStrategy shippingStrategy;

    public ShippingResult calculateShippingCost(Parcel parcel) {
        return shippingStrategy.calculateShipping(parcel);
    }

    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }

}
