package com.lldpractice.designpatterns.structural.bridge;

import com.lldpractice.designpatterns.structural.bridge.devices.Device;
import com.lldpractice.designpatterns.structural.bridge.devices.Laptop;
import com.lldpractice.designpatterns.structural.bridge.devices.MobilePhone;
import com.lldpractice.designpatterns.structural.bridge.devices.SmartTV;
import com.lldpractice.designpatterns.structural.bridge.streamingquality.BasicOnDevice;
import com.lldpractice.designpatterns.structural.bridge.streamingquality.HdOnDevice;
import com.lldpractice.designpatterns.structural.bridge.streamingquality.UltraOnDevice;
import com.lldpractice.designpatterns.structural.bridge.streamingquality.VideoQuality;

public class VideoAppBridge {

    public static void main(String args[])
    {

        Device smartTV = new SmartTV();
        Device mobilePhone = new MobilePhone();
        Device laptop = new Laptop();

        System.out.println("=== Basic Quality on Smart TV ===");

        VideoQuality basicOnTV = new BasicOnDevice(smartTV);
        basicOnTV.play();
        basicOnTV.displayInfo();
        basicOnTV.pause();

        System.out.println("\n=== HD Quality on Mobile ===");

        VideoQuality hdOnMobile = new HdOnDevice(mobilePhone);
        hdOnMobile.play();
        hdOnMobile.displayInfo();
        hdOnMobile.pause();

        System.out.println("\n=== 4K Quality on Laptop ===");
        VideoQuality ultraOnLaptop = new UltraOnDevice(laptop);
        ultraOnLaptop.play();
        ultraOnLaptop.displayInfo();
        ultraOnLaptop.pause();

        // Runtime switching - change device for same quality
        System.out.println("\n=== Switching HD from Mobile to Smart TV ===");
        VideoQuality hdQuality = new HdOnDevice(smartTV);
        hdQuality.play();
        hdQuality.displayInfo();

    }
}
