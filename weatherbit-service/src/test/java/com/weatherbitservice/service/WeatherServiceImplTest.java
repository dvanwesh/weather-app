package com.weatherbitservice.service;

import com.weatherbitservice.dto.DailyWeatherData;
import com.weatherbitservice.dto.WeatherData;
import com.weatherbitservice.dto.weatherbit.WeatherbitData;
import com.weatherbitservice.dto.weatherbit.WeatherbitResponse;
import com.weatherbitservice.webclient.QueryType;
import com.weatherbitservice.webclient.WeatherBitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherServiceImplTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherBitService weatherBitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrentWeatherByCity() {
        // Mock WeatherBitService response
        WeatherbitResponse weatherbitResponse = new WeatherbitResponse();
        WeatherbitData weatherbitData = new WeatherbitData();
        weatherbitData.setCityName("Atlanta");
        weatherbitData.setDatetime("2023-10-14:12");
        weatherbitResponse.setData(Collections.singletonList(weatherbitData));

        // Mock the WeatherBitService
        Mockito.when(weatherBitService.getCurrentWeather(Mockito.eq(QueryType.CITY), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Mono.just(weatherbitResponse));

        // Perform the test
        Mono<WeatherData> result = weatherService.getCurrentWeatherByCity("City", "I");

        // Verify the result
        WeatherData weatherData = result.block();
        assertEquals("Atlanta", weatherData.getCity());
    }

    @Test
    public void testGetDailyWeatherByCity() {
        // Mock WeatherBitService response
        WeatherbitResponse weatherbitResponse = new WeatherbitResponse();
        WeatherbitData weatherbitData = new WeatherbitData();
        weatherbitData.setCityName("Atlanta");
        weatherbitData.setDatetime("2023-10-14:12");
        weatherbitData.setMaxTemp(74.00);
        weatherbitData.setMinTemp(54.00);
        weatherbitResponse.setCityName("Atlanta");
        weatherbitResponse.setData(Collections.singletonList(weatherbitData));

        // Mock the WeatherBitService
        Mockito.when(weatherBitService.getDailyWeather(Mockito.eq(QueryType.CITY), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Mono.just(weatherbitResponse));

        // Perform the test
        Mono<DailyWeatherData> result = weatherService.getDailyWeatherByCity("City", "I");

        // Verify the result
        DailyWeatherData dailyWeatherData = result.block();
        WeatherData weatherData = dailyWeatherData.getData().get(0);
        assertEquals(74.00, weatherData.getMaxTemperature());
    }
}

