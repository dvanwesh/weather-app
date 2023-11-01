package com.weatherbitservice.webclient;

import com.weatherbitservice.dto.weatherbit.WeatherbitResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherBitServiceImplTest {

    private static MockWebServer mockWebServer;
    private static WeatherBitServiceImpl weatherBitService;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeAll
    public static void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/weather").toString())
                .build();
        weatherBitService = new WeatherBitServiceImpl();
        weatherBitService.setWebClient(webClient);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetCurrentWeather() throws IOException, InterruptedException {
        // Load the JSON file from the test/resources directory
        Resource resource = resourceLoader.getResource("classpath:currentWeatherResponse.json");

        try (InputStream inputStream = resource.getInputStream()) {
            String jsonData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Define the mock response using the JSON data
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(jsonData)
                    .addHeader("Content-Type", "application/json"));

            // Perform the actual call to the service
            Mono<WeatherbitResponse> response = weatherBitService.getCurrentWeather(QueryType.CITY, "Atlanta", "I");

            // Assertions
            WeatherbitResponse weatherbitResponse = response.block(); // block to get the actual response
            assertEquals(1, weatherbitResponse.getCount());
            assertEquals("Atlanta", weatherbitResponse.getData().get(0).getCityName());
            assertEquals(9.2, weatherbitResponse.getData().get(0).getTemp());

            // confirm that the app made the HTTP requests to right endpoints.
            RecordedRequest request = mockWebServer.takeRequest();
            assertEquals("/weather/current?city=Atlanta&key&units=I", request.getPath());
        }
    }

    @Test
    public void testGetDailyWeather() throws IOException, InterruptedException {
        // Load the JSON file from the test/resources directory
        Resource resource = resourceLoader.getResource("classpath:tenDayForecastResponse.json");

        try (InputStream inputStream = resource.getInputStream()) {
            String jsonData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Define the mock response using the JSON data
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(jsonData)
                    .addHeader("Content-Type", "application/json"));

            // Perform the actual call to the service
            Mono<WeatherbitResponse> response = weatherBitService.getDailyWeather(QueryType.ZIPCODE, "30309", "I");

            // Assertions
            WeatherbitResponse weatherbitResponse = response.block(); // block to get the actual response
            assertEquals(10, weatherbitResponse.getData().size());
            assertEquals("Atlanta", weatherbitResponse.getCityName());

            // confirm that the app made the HTTP requests to right endpoints.
            RecordedRequest request = mockWebServer.takeRequest();
            assertEquals("/weather/forecast/daily?postal_code=30309&key&days=10&units=I", request.getPath());
        }
    }
}
