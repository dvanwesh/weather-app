package com.weatherbitservice.webclient;

import com.weatherbitservice.dto.weatherbit.WeatherbitResponse;
import reactor.core.publisher.Mono;

public interface WeatherBitService {

    /**
     * Retrieves current weather data from the Weatherbit API https://www.weatherbit.io/api/weather-current
     *
     * @param queryType The query type, either QueryType.CITY or QueryType.ZIPCODE.
     * @param value The city name or ZIP code to query.
     * @param units The desired unit of measurement (optional).
     * @return A Mono emitting the response containing current weather data.
     */
    Mono<WeatherbitResponse> getCurrentWeather(QueryType queryType, String value, String units);

    /**
     * Retrieves daily weather data from the Weatherbit API https://www.weatherbit.io/api/weather-forecast-16-day
     *
     * @param queryType The query type, either QueryType.CITY or QueryType.ZIPCODE.
     * @param value The city name or ZIP code to query.
     * @param units The desired unit of measurement (optional).
     * @return A Mono emitting the response containing daily weather data.
     */
    Mono<WeatherbitResponse> getDailyWeather(QueryType queryType, String value, String units);
}
