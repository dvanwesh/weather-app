package com.weatherbitservice.webclient;

import com.weatherbitservice.dto.weatherbit.WeatherbitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
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
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(WebClientResponseException.create(clientResponse.statusCode().value(), clientResponse.statusCode().toString(), clientResponse.headers().asHttpHeaders(), null, null))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(WebClientResponseException.create(clientResponse.statusCode().value(), clientResponse.statusCode().toString(), clientResponse.headers().asHttpHeaders(), null, null))
                )
                .bodyToMono(WeatherbitResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("exception", e);
                    return Mono.error(new RuntimeException("WebClient error occurred", e));
                });
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
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(WebClientResponseException.create(clientResponse.statusCode().value(), clientResponse.statusCode().toString(), clientResponse.headers().asHttpHeaders(), null, null))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(WebClientResponseException.create(clientResponse.statusCode().value(), clientResponse.statusCode().toString(), clientResponse.headers().asHttpHeaders(), null, null))
                )
                .bodyToMono(WeatherbitResponse.class)
                .onErrorResume(WebClientResponseException.class, Mono::error);

    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
