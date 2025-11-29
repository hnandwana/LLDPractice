package com.lldpractice.designpatterns.behavioural.observer.displayobserver;

import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;

public class FaultyDisplay implements Observer {
    private WeatherStation weatherStation;

    public FaultyDisplay(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }

    @Override
    public void update() {
        // 30% chance of failure
        if (Math.random() < 0.3) {
            throw new RuntimeException("Simulated random failure!");
        }

        System.out.println("Faulty Display:");
        System.out.println("Temperature: " + weatherStation.getTemperature() + "°C");
        System.out.println("(Working correctly this time)");
        System.out.println("---");
        System.out.println();
    }

    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}
