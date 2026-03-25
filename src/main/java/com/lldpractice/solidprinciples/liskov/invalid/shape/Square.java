package com.lldpractice.solidprinciples.liskov.invalid.shape;

// Square IS-A Rectangle... right? Mathematically yes, but in code — NO!
class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // ⚠️ Forces height = width
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height;  // ⚠️ Forces width = height
    }
}