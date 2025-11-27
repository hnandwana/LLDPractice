package com.lldpractice.designpatterns.structural.bridge.devices;

public class Laptop implements Device{
    @Override
    public String getDeviceName() {
        return "Laptop";
    }

    @Override
    public String getDeviceDescription() {
        return "Laptop (Press Space to pause)";
    }

    @Override
    public String getDeviceSpecifications() {
        return "with keyboard controls";
    }
}
