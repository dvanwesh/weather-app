package com.weatherbitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DailyWeatherData {

    private List<WeatherData> data;
}
