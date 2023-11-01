package weatherservice.service;

import com.weatherservice.dto.DailyWeatherData;
import com.weatherservice.dto.WeatherData;
import com.weatherservice.dto.weatherbit.WeatherbitData;
import com.weatherservice.dto.weatherbit.WeatherbitResponse;
import com.weatherservice.exception.DataExtractionException;
import com.weatherservice.webclient.QueryType;
import com.weatherservice.webclient.WeatherBitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherBitService weatherBitService;

    @Override
    public Mono<WeatherData> getCurrentWeatherByCity(String city, String units) {
        return getCurrentWeather(QueryType.CITY, city, units);
    }

    @Override
    public Mono<WeatherData> getCurrentWeatherByZipcode(String zipcode, String units) {
        return getCurrentWeather(QueryType.ZIPCODE, zipcode, units);
    }

    @Override
    public Mono<DailyWeatherData> getDailyWeatherByCity(String city, String units) {
        return getDailyWeather(QueryType.CITY, city, units);
    }

    @Override
    public Mono<DailyWeatherData> getDailyWeatherByZipcode(String zipcode, String units) {
        return getDailyWeather(QueryType.ZIPCODE, zipcode, units);
    }

    private Mono<DailyWeatherData> getDailyWeather(QueryType queryType, String value, String units) {
        return weatherBitService.getDailyWeather(queryType, value, units)
                .map(response -> {
                    List<WeatherData> weatherDataList = response.getData().stream()
                            .map(data -> {
                                try {
                                    return toWeatherData(response.getCityName(), data, "yyyy-MM-dd");
                                } catch (ParseException e) {
                                    log.error("Failed to extract weather data", e);
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toList());
                    return new DailyWeatherData(weatherDataList);
                });
    }

    private Mono<WeatherData> getCurrentWeather(QueryType queryType, String value, String units) {
        return weatherBitService.getCurrentWeather(queryType, value, units)
                .flatMap(response -> {
                    try {
                        WeatherData weatherData = extractWeatherData(response);
                        return Mono.just(weatherData);
                    } catch (DataExtractionException | ParseException e) {
                        log.error("Failed to extract weather data", e);
                        return Mono.error(e);
                    }
                });
    }

    private WeatherData extractWeatherData(WeatherbitResponse response) throws DataExtractionException, ParseException {
        if (response.getData() != null && !response.getData().isEmpty()) {
            WeatherbitData data = response.getData().get(0);
            return toWeatherData(null, data, "yyyy-MM-dd:HH");
        } else {
            throw new DataExtractionException("Failed to extract weather data");
        }
    }

    private WeatherData toWeatherData(String cityName, WeatherbitData data, String datePattern) throws ParseException {
        return WeatherData.builder()
                .city(cityName != null ? cityName : data.getCityName())
                .dateTime(parseAndFormatDateTime(data.getDatetime(), datePattern))
                .description(data.getWeather().getDescription())
                .temperature(data.getTemp())
                .minTemperature(data.getMinTemp())
                .maxTemperature(data.getMaxTemp())
                .pressure(data.getPres())
                .humidity(data.getRh())
                .windSpeed(data.getWindSpd())
                .windDirection(data.getWindCdir())
                .windDirectionFull(data.getWindCdirFull())
                .windGusts(Optional.of(data.getGust()).filter(gust -> gust != 0.0).orElse(data.getWindGustSpd()))
                .build();
    }

    private String parseAndFormatDateTime(String dateStr, String datePattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:HH");
        if (!dateStr.contains(":")) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date = formatter.parse(dateStr);

        SimpleDateFormat newFormatter = new SimpleDateFormat("EEEE, MMMM dd yyyy");
        return newFormatter.format(date);
    }
}
