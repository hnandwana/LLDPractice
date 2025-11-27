package com.lldpractice.designpatterns.structural.adapter.weather;

import com.lldpractice.designpatterns.structural.adapter.weather.model.WeatherInfo;
import com.lldpractice.designpatterns.structural.adapter.weather.service.AccuWeatherService;
import com.lldpractice.designpatterns.structural.adapter.weather.service.OpenWeatherAPI;
import com.lldpractice.designpatterns.structural.adapter.weather.service.WeatherAPIService;
import com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter.AccuWeatherServiceAdapter;
import com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter.OpenWeatherApiAdapter;
import com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter.WeatherApiServiceAdapter;
import com.lldpractice.designpatterns.structural.adapter.weather.weatheradapter.WeatherService;

public class WeatherApp {
    public static void main(String[] args)  {
        String city = "Delhi";
        WeatherService accuWeather = new AccuWeatherServiceAdapter(new AccuWeatherService());
        WeatherInfo info1 = accuWeather.getWeather(city);
        System.out.println(info1);

        WeatherService openWeatherApi = new OpenWeatherApiAdapter(new OpenWeatherAPI());
        WeatherInfo info2 = openWeatherApi.getWeather(city);
        System.out.println(info2);


        WeatherService weatherApiService = new WeatherApiServiceAdapter(new WeatherAPIService());
        WeatherInfo info3 = weatherApiService.getWeather(city);
        System.out.println(info3);

    }
}
