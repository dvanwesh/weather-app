package com.weatherbitservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherData {

    private String city;

    private String dateTime;

    private String description;

    private double temperature;

    private double minTemperature;

    private double maxTemperature;

    private int pressure;

    private int humidity;

    private double windSpeed;

    private String windDirection;

    private String windDirectionFull;

    private double windGusts;

}
