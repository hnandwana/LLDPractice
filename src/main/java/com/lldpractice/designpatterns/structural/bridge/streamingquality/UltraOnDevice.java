package com.lldpractice.designpatterns.structural.bridge.streamingquality;

import com.lldpractice.designpatterns.structural.bridge.devices.Device;

public class UltraOnDevice extends VideoQuality {

    public UltraOnDevice(Device device) {
        super(device);
    }

    @Override
    public void play() {
        System.out.println("[" + device.getDeviceName() + "]" + " Playing video in 2160p " + device.getDeviceSpecifications());
    }

    @Override
    public void displayInfo() {
        System.out.println("Video Info: 4K Ultra (2160p) | Bitrate: 8000 kbps | Device: " + device.getDeviceDescription());
    }

    @Override
    public void pause() {
        System.out.println("[" + device.getDeviceName() + "] Video paused");
    }
}
