package weatherservice.service;

import com.weatherservice.dto.DailyWeatherData;
import com.weatherservice.dto.WeatherData;
import reactor.core.publisher.Mono;

public interface WeatherService {

    /**
     * Retrieves the current weather data for a city.
     *
     * @param city  The name of the city for which to fetch weather data.
     * @param units The units in which to retrieve weather data (e.g., "metric" for Celsius).
     * @return A {@link Mono} emitting the current weather data for the specified city.
     */
    Mono<WeatherData> getCurrentWeatherByCity(String city, String units);

    /**
     * Retrieves the current weather data for a city using its ZIP code.
     *
     * @param zipcode The ZIP code of the city for which to fetch weather data.
     * @param units   The units in which to retrieve weather data (e.g., "metric" for Celsius).
     * @return A {@link Mono} emitting the current weather data for the specified city based on its ZIP code.
     */
    Mono<WeatherData> getCurrentWeatherByZipcode(String zipcode, String units);

    /**
     * Retrieves the daily weather forecast for a city.
     *
     * @param city  The name of the city for which to fetch the daily weather forecast.
     * @param units The units in which to retrieve weather data (e.g., "metric" for Celsius).
     * @return A {@link Mono} emitting the daily weather forecast data for the specified city.
     */
    Mono<DailyWeatherData> getDailyWeatherByCity(String city, String units);

    /**
     * Retrieves the daily weather forecast for a city using its ZIP code.
     *
     * @param zipcode The ZIP code of the city for which to fetch the daily weather forecast.
     * @param units   The units in which to retrieve weather data (e.g., "metric" for Celsius).
     * @return A {@link Mono} emitting the daily weather forecast data for the specified city based on its ZIP code.
     */
    Mono<DailyWeatherData> getDailyWeatherByZipcode(String zipcode, String units);

}
