package weatherservice.controller;

import com.weatherservice.dto.DailyWeatherData;
import com.weatherservice.dto.WeatherData;
import com.weatherservice.service.WeatherService;
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

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Endpoint to generate current weather data
     *
     * @param city name of the city anywhere in the world
     * @param zipcode zipcode
     * @param units units M/I
     * @return WeatherData
     */
    @GetMapping("/current")
    @ExceptionHandler(Exception.class)
    public Mono<WeatherData> getCurrentWeather(@RequestParam(required = false) String city,
                                               @RequestParam(required = false) String zipcode,
                                               @RequestParam(required = false) String units) {
        if (city != null) {
            return weatherService.getCurrentWeatherByCity(city, units);
        } else if (zipcode != null) {
            return weatherService.getCurrentWeatherByZipcode(zipcode, units);
        } else {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "City or ZIP code is required"));
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
        if (city != null) {
            return weatherService.getDailyWeatherByCity(city, units);
        } else if (zipcode != null) {
            return weatherService.getDailyWeatherByZipcode(zipcode, units);
        } else {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "City or ZIP code is required"));
        }
    }
}

