package com.lldpractice.designpatterns.behavioural.observer.displayobserver;

import com.lldpractice.designpatterns.behavioural.observer.observable.WeatherStation;

public class StatisticsDisplay implements Observer {
    private final WeatherStation weatherStation;
    private float minTemp = Float.POSITIVE_INFINITY;  // FIX: Proper initialization
    private float maxTemp = Float.NEGATIVE_INFINITY;  // FIX: Proper initialization
    private float tempSum = 0.0f;
    private int numReadings = 0;

    public StatisticsDisplay(WeatherStation weatherStation, float minTemp, float maxTemp, float tempSum, int numReadings) {
        this.weatherStation = weatherStation;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.tempSum = tempSum;
        this.numReadings = numReadings;
        weatherStation.registerObserver(this);
    }

    @Override
    public void update() {
        float currentTemp = weatherStation.getTemperature();
        tempSum += currentTemp;
        numReadings++;
        if (currentTemp < minTemp) {
            minTemp = currentTemp;
        }
        if (currentTemp > maxTemp) {
            maxTemp = currentTemp;
        }
        float avgTemp = tempSum / numReadings;

        System.out.println("Statistics Display: ");
        System.out.println("Temperature Stats: ");
        System.out.println("Average: " + avgTemp);
        System.out.println("Max: " + maxTemp);
        System.out.println("Min: " + minTemp);
        System.out.println("Readings: " + numReadings);
        System.out.println("---");
        System.out.println("");
    }

    public void displayStats() {
        System.out.println("Temperature Stats:");
        System.out.printf("Average: %.1f°C%n", (tempSum / numReadings));
        System.out.printf("Max: %.1f°C%n", maxTemp);
        System.out.printf("Min: %.1f°C%n", minTemp);
        System.out.println("Readings: " + numReadings);
    }

    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}