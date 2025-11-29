package com.lldpractice.designpatterns.behavioural.strategy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Parcel {
    private double weight;
    private int distance;
    private int orderTotal;

}
