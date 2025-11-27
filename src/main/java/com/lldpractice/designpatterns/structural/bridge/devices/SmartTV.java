package com.lldpractice.designpatterns.structural.bridge.devices;

public class SmartTV implements Device
{

    @Override
    public String getDeviceName() {
        return "Smart TV";
    }

    @Override
    public String getDeviceDescription() {
        return "Smart TV with Surround Sound";
    }

    @Override
    public String getDeviceSpecifications() {
        return "on 55-inch display";
    }
}
