package com.lldpractice.designpatterns.structural.bridge.streamingquality;

import com.lldpractice.designpatterns.structural.bridge.devices.Device;

public class HdOnDevice extends VideoQuality {

    public HdOnDevice(Device device) {
        super(device);
    }

    @Override
    public void play() {
        System.out.println("[" + device.getDeviceName() + "]" + " Playing video in 1080p " + device.getDeviceSpecifications());
    }

    @Override
    public void displayInfo() {
        System.out.println("Video Info: HD Quality (1080p) | Bitrate: 3000 kbps | Device: " + device.getDeviceDescription());
    }

    @Override
    public void pause() {
        System.out.println("[" + device.getDeviceName() + "] Video paused");
    }
}
