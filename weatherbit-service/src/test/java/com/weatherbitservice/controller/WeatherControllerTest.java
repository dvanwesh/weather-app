package com.weatherbitservice.controller;

import com.weatherbitservice.service.WeatherService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentWeather_BadRequest_InvalidUnits() {
        String city = "Atlanta";
        String units = "invalid"; // Invalid units
        when(weatherService.getCurrentWeatherByCity(city, units))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong units submitted, valid units are I and M"));

        Assert.assertThrows(ResponseStatusException.class, () -> {
            weatherController.getCurrentWeather(city, null, units).block();
        });
    }

    @Test
    public void testGetCurrentWeather_BadRequest_InvalidCity() {
        String city = "123InvalidCity"; // Invalid city name
        when(weatherService.getCurrentWeatherByCity(city, null))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "City should contain only letters and spaces"));

        Assert.assertThrows(ResponseStatusException.class, () -> {
            weatherController.getCurrentWeather(city, null, null).block();
        });
    }

    @Test
    public void testGetCurrentWeather_BadRequest_NoParams() {
        when(weatherService.getCurrentWeatherByZipcode(null, null))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "City or ZIP code is required"));

        Assert.assertThrows(ResponseStatusException.class, () -> {
            weatherController.getCurrentWeather(null, null, null).block();
        });
    }
}
