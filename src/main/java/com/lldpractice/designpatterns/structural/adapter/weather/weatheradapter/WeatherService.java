package com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter;

import com.lldpractice.designpatterns.structural.adapter.weather.model.WeatherInfo;

public interface WeatherService {
    WeatherInfo getWeather(String city);
}
