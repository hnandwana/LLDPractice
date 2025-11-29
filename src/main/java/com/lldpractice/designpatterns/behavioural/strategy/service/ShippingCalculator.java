package com.lldpractice.designpatterns.behavioural.strategy.service;

import com.lldpractice.designpatterns.behavioural.strategy.model.Parcel;
import com.lldpractice.designpatterns.behavioural.strategy.model.ShippingResult;
import com.lldpractice.designpatterns.behavioural.strategy.shippingstrategy.ShippingStrategy;
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
