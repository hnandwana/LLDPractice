package com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter;

import com.lldpractice.designpatterns.structural.adapter.weather.model.WeatherInfo;
import com.lldpractice.designpatterns.structural.adapter.weather.service.OpenWeatherAPI;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenWeatherApiAdapter implements WeatherService {

    private final OpenWeatherAPI openWeatherAPI;

    @Override
    public WeatherInfo getWeather(String city) {
        OpenWeatherAPI.WeatherData weatherData = openWeatherAPI.fetchWeather(city);
        return new WeatherInfo(weatherData.getTemperature()-273.15, weatherData.getCity(), weatherData.getCondition());
    }
}
