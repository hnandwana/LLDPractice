package com.lldpractice.designpatterns.structural.bridge.streamingquality;

import com.lldpractice.designpatterns.structural.bridge.devices.Device;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class VideoQuality {
    protected final Device device;

    public abstract void play();

    public abstract void displayInfo();

    public abstract void pause();

}