package com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter;

import com.lldpractice.designpatterns.structural.adapter.weather.model.WeatherInfo;
import com.lldpractice.designpatterns.structural.adapter.weather.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeatherApiServiceAdapter implements WeatherService {

    private final WeatherAPIService weatherAPIService;

    @Override
    public WeatherInfo getWeather(String city) {
        String weatherData = weatherAPIService.getCurrentWeather(city);
        String[] parts = weatherData.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String cityName = parts[0].split(":")[1];
        double tempF = Double.parseDouble(parts[1].split(":")[1]);
        double tempC = (tempF - 32) * 5 / 9;
        String condition = parts[2].split(":")[1];
        return new WeatherInfo(tempC, cityName, condition);
    }
}
