package com.lldpractice.designpatterns.structural.adapter.weather.service;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AccuWeatherService {
    public AccuWeatherResponse getWeatherInfo(String city) {
        System.out.println("AccuWeather: Retrieving info for " + city);
        return new AccuWeatherResponse(city, 25.0, "Partly Cloudy");
    }

    @AllArgsConstructor
    @Data
    public static class AccuWeatherResponse {
        private String location;
        private double tempCelsius;
        private String weather;

    }
}
