package com.lldpractice.designpatterns.structural.bridge.streamingquality;


import com.lldpractice.designpatterns.structural.bridge.devices.Device;

public class BasicOnDevice extends VideoQuality {

    public BasicOnDevice(Device device) {
        super(device);
    }

    @Override
    public void play() {
        System.out.println("[" + device.getDeviceName() + "]" + " Playing video in 480p " + device.getDeviceSpecifications());
    }

    @Override
    public void displayInfo() {

        System.out.println("Video Info: Basic Quality (480p) | Bitrate: 800 kbps | Device: " + device.getDeviceDescription());
    }

    @Override
    public void pause() {

        System.out.println("[" + device.getDeviceName() + "] Video paused");
    }
}
