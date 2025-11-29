package com.lldpractice.designpatterns.behavioural.observer;

import com.lldpractice.designpatterns.behavioural.observer.displayobserver.*;
import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;

public class WeatherStationDemo {

    public static void main(String[] args) {
        // Create weather station (subject)
        WeatherStation weatherStation = new WeatherStation();

        // Create displays (observers)
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);
        StatisticsDisplay statsDisplay = new StatisticsDisplay(weatherStation, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0, 0);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherStation, 1013.25f);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherStation);


        // Displays register themselves in constructor
        System.out.println("=== Initial Weather Update ===");
        weatherStation.setMeasurements(25.5f, 65f, 1013.2f);

        System.out.println("\n=== Weather Change 1 ===");
        weatherStation.setMeasurements(28.0f, 70f, 1012.0f);

        System.out.println("\n=== Weather Change 2 ===");
        weatherStation.setMeasurements(22.0f, 55f, 1015.5f);

        // Remove forecast display
        System.out.println("\n=== Removing Forecast Display ===");
        weatherStation.removeObserver(forecastDisplay);

        System.out.println("\n=== Weather Change 3 (no forecast) ===");
        weatherStation.setMeasurements(30.0f, 80f, 1010.0f);

        // Add faulty display to test exception handling
        System.out.println("\n=== Adding Faulty Display ===");
        FaultyDisplay faultyDisplay = new FaultyDisplay(weatherStation);

        System.out.println("\n=== Weather Change 4 (with faulty display) ===");
        weatherStation.setMeasurements(26.0f, 68f, 1014.0f);

        // Show statistics summary
        System.out.println("\n=== Final Statistics ===");
        statsDisplay.displayStats();
    }

}
