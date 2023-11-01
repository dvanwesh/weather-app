package com.weatherbitservice.controller;

import com.weatherbitservice.dto.DailyWeatherData;
import com.weatherbitservice.dto.WeatherData;
import com.weatherbitservice.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST})
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Endpoint to generate current weather data
     *
     * @param city    name of the city anywhere in the world
     * @param zipcode zipcode
     * @param units   units M/I
     * @return WeatherData
     */
    @GetMapping("/current")
    @ExceptionHandler(Exception.class)
    public Mono<WeatherData> getCurrentWeather(@RequestParam(required = false) String city,
                                               @RequestParam(required = false) String zipcode,
                                               @RequestParam(required = false) String units) {
        validateParams(city, zipcode, units);
        if (city != null) {
            return weatherService.getCurrentWeatherByCity(city, units);
        } else {
            return weatherService.getCurrentWeatherByZipcode(zipcode, units);
        }
    }

    /**
     * Endpoint to generate weather forecast for next 10 days
     *
     * @param city
     * @param zipcode
     * @param units
     * @return
     */
    @GetMapping("/daily")
    public Mono<DailyWeatherData> getDailyWeather(@RequestParam(required = false) String city,
                                                  @RequestParam(required = false) String zipcode,
                                                  @RequestParam(required = false) String units) {

        validateParams(city, zipcode, units);
        if (city != null) {
            return weatherService.getDailyWeatherByCity(city, units);
        } else {
            return weatherService.getDailyWeatherByZipcode(zipcode, units);
        }
    }

    private void validateParams(String city, String zipcode, String units) {
        if (units != null) {
            if ((!units.equals("I") && !units.equals("M"))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong units submitted, valid units are I and M");
            }
        }
        if (city != null) {
            if (!city.matches("^[A-Za-z ]+$")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City should contain only letters and spaces");
            }
        } else if (zipcode != null) {
            if (!zipcode.matches("^[0-9]*$")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zip code should contain only digits.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City or ZIP code is required");
        }
    }
}

