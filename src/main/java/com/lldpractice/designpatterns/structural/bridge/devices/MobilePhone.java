package com.lldpractice.designpatterns.structural.bridge.devices;

public class MobilePhone implements Device {

    @Override
    public String getDeviceName() {
        return "Mobile Phone";
    }

    @Override
    public String getDeviceDescription() {
        return "Mobile (Battery Saver Mode)";
    }

    @Override
    public String getDeviceSpecifications() {
        return "on 6-inch touchscreen";
    }
}
