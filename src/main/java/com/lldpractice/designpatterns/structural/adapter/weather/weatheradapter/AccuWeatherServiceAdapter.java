package com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter;

import com.lldpractice.designpatterns.structural.adapter.weather.model.WeatherInfo;
import com.lldpractice.designpatterns.structural.adapter.weather.service.AccuWeatherService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccuWeatherServiceAdapter implements WeatherService {

    private final AccuWeatherService accuWeatherService;

    @Override
    public WeatherInfo getWeather(String city) {
        AccuWeatherService.AccuWeatherResponse accuWeatherResponse = accuWeatherService.getWeatherInfo(city);
        return new WeatherInfo(accuWeatherResponse.getTempCelsius(), accuWeatherResponse.getLocation(), accuWeatherResponse.getWeather());
    }
}
