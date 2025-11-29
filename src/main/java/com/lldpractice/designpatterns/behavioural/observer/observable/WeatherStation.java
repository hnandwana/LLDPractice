package com.lldpractice.designpatterns.behavioural.observer.observable;

import com.lldpractice.designpatterns.behavioural.observer.displayobserver.Observer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeatherStation implements Subject {

    private float temperature;
    private float humidity;
    private float pressure;
    private List<Observer> observers = new ArrayList<>();

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        System.out.println("[Weather Station] Measurements updated: Temp=" + temperature + "°C, " + "Humidity=" + humidity + "%, " + "Pressure=" + pressure + "hPa");
        System.out.println("[Weather Station] Notifying " + observers.size() + " observers...");
        System.out.println();
        notifyObservers();

    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        observers.add(observer);
        System.out.println("[WeatherStation] Observer registered: " +
                observer.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("[WeatherStation] Observer removed: " +
                    observer.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        // KEY FIX: Exception handling to ensure all observers get notified
        for (Observer observer : observers) {
            try {
                observer.update();
            } catch (Exception e) {
                System.err.println("[ERROR] Observer " +
                        observer.getClass().getSimpleName() +
                        " failed: " + e.getMessage());
                // Continue with other observers
            }
        }
    }
}
