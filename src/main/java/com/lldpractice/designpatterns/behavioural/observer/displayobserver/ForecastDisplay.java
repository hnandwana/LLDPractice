package com.lldpractice.designpatterns.behavioural.observer.displayobserver;

import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;

public class ForecastDisplay implements Observer {

    private WeatherStation weatherStation;
    private float lastPressure;

    public ForecastDisplay(WeatherStation weatherStation, float lastPressure) {
        this.weatherStation = weatherStation;
        this.lastPressure = lastPressure;
        weatherStation.registerObserver(this);
    }

    @Override
    public void update() {
        float currentPressure = weatherStation.getPressure();
        System.out.print("Forecast Display: ");
        System.out.println("Pressure: " + weatherStation.getPressure() + " hPa");
        if (currentPressure > lastPressure) {
            System.out.println("Forecast: Improving weather expected.");
        } else if (currentPressure < lastPressure) {
            System.out.println("Forecast: Cooler, rainy weather expected.");
        } else {
            System.out.println("Forecast: More of the same.");
        }
        lastPressure = currentPressure;
        System.out.println("--------");
    }


    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}