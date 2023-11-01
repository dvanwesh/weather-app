package weatherservice.webclient;

import com.weatherservice.dto.weatherbit.WeatherbitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class WeatherBitServiceImpl implements WeatherBitService {

    @Autowired
    private WebClient webClient;

    @Value("${weather.api.apikey}")
    private String apiKey;

    public Mono<WeatherbitResponse> getCurrentWeather(QueryType queryType, String value, String units) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current")
                        .queryParam(queryType.getValue(), value)
                        .queryParam("key", apiKey)
                        .queryParamIfPresent("units", Optional.ofNullable(units))
                        .build())
                .retrieve()
                .bodyToMono(WeatherbitResponse.class);

    }

    @Override
    public Mono<WeatherbitResponse> getDailyWeather(QueryType queryType, String value, String units) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast/daily")
                        .queryParam(queryType.getValue(), value)
                        .queryParam("key", apiKey)
                        .queryParam("days", 10)
                        .queryParamIfPresent("units", Optional.ofNullable(units))
                        .build())
                .retrieve()
                .bodyToMono(WeatherbitResponse.class);

    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
