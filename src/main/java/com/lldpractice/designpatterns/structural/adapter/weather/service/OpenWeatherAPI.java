package com.lldpractice.designpatterns.structural.adapter.weather.service;

import lombok.AllArgsConstructor;
import lombok.Data;

public class OpenWeatherAPI {
    public WeatherData fetchWeather(String cityName) {
        System.out.println("OpenWeather: Fetching data for " + cityName);
        // Returns temperature in Kelvin
        return new WeatherData(cityName, 298.15, "Kelvin", "Sunny");
    }

    @AllArgsConstructor
    @Data
    public static class WeatherData {
        private String city;
        private double temperature;
        private String unit;
        private String condition;
    }
}
