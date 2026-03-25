package com.lldpractice.solidprinciples.liskov.valid.shape;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Rectangle implements Shape {

    private int width;
    private int length;

    @Override
    public int getArea() {
        return width * length;
    }
}
