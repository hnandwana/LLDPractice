package com.lldpractice.designpatterns.structural.adapter.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherInfo {

    private double temperatureCelsius;
    private String city;
    private String weather;

}