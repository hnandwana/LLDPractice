package com.lldpractice.solidprinciples.liskov.valid.shape;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Square implements Shape {
    private final int side;


    @Override
    public int getArea() {
        return side * side;
    }
}