package com.lldpractice.designpatterns.structural.adapter.weather.service;

public class WeatherAPIService {
    public String getCurrentWeather(String location) {
        System.out.println("WeatherAPI: Getting weather for " + location);
        // Returns JSON-like string with Fahrenheit
        return "{\"location\":\"" + location + "\",\"temp_f\":75.0,\"condition\":\"Cloudy\"}";
    }
}
