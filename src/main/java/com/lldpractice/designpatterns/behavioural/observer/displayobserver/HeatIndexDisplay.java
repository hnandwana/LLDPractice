package com.lldpractice.designpatterns.behavioural.observer.displayobserver;

import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;
import lombok.AllArgsConstructor;

public class HeatIndexDisplay implements Observer {

    private WeatherStation weatherStation;

    public HeatIndexDisplay(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }

    @Override
    public void update() {
        float temperature = weatherStation.getTemperature();
        float humidity = weatherStation.getHumidity();
        float heatIndex = temperature + (0.5f * humidity);

        System.out.println("Heat Index Display:");
        System.out.println("Heat Index: " + heatIndex);
        System.out.println("Status: " + (heatIndex > 40 ? "⚠️ Extreme heat warning!" : "Normal conditions"));
        System.out.printf("-----------");
    }

    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}