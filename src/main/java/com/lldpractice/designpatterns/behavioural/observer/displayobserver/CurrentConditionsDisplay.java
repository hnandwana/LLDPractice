package com.lldpractice.designpatterns.behavioural.observer.displayobserver;

import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;

public class CurrentConditionsDisplay implements Observer {
    private WeatherStation weatherStation;

    public CurrentConditionsDisplay(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }

    @Override
    public void update() {
        System.out.println("Current conditions Display: ");
        System.out.println("Temperature: " + weatherStation.getTemperature() + "°C");
        System.out.println("Humidity: " + weatherStation.getHumidity() + "% humidity");
        System.out.println("Pressure: " + weatherStation.getPressure() + " hPa");
        System.out.println("---");
        System.out.println();
    }

    // Cleanup method for proper unregistration
    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}
