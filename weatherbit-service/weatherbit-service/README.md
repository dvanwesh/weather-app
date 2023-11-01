# Weatherbit Service - README

Welcome to the Weatherbit Service project! This service is designed to fetch weather data from third-party APIs provided by [Weatherbit](https://www.weatherbit.io/api/swaggerui/weather-api-v2). The service exposes two endpoints for the React frontend application, weather-app-ui, to consume.

## Table of Contents

1. [Getting Started](#getting-started)
2. [API Endpoints](#api-endpoints)
3. [Build and Deploy](#build-and-deploy)
4. [Development](#development)

## Getting Started

Before you begin using the Weather Service, make sure you have the following prerequisites installed:

To specify Java 17 as a requirement in your README file, you can add a "Requirements" section to clearly indicate what software and versions are needed to run the Weather Service. Here's an example of how to do this:

markdown

## Requirements

Before you begin using the Weather Service, make sure you have the following software and tools installed:

- **Java Development Kit (JDK) 17**: You can download the latest version of Java 17 from [OpenJDK](https://adoptium.net/releases.html?variant=openjdk17&jvmVariant=hotspot).
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)

## API Endpoints

The Weather Service provides two main endpoints to retrieve weather data:

1. **Current Weather Data**

  - **Endpoint**: `/weather/current`
  - **Description**: Retrieves the current weather data.
  - **Parameters**:
    - `city` (Optional): Name of the city anywhere in the world.
    - `zipcode` (Optional): ZIP code.
    - `units` (Optional): Units for temperature (M for Metric, I for Imperial).
  - **HTTP Method**: GET
  - **Response**: WeatherData

2. **Daily Weather Forecast**

  - **Endpoint**: `/weather/daily`
  - **Description**: Retrieves the daily weather forecast for the next 10 days.
  - **Parameters**:
    - `city` (Optional): Name of the city anywhere in the world.
    - `zipcode` (Optional): ZIP code.
    - `units` (Optional): Units for temperature (M for Metric, I for Imperial).
  - **HTTP Method**: GET
  - **Response**: DailyWeatherData

## Build and Deploy

To build and deploy the Weather Service, follow these steps:

1. Clone the project repository:
   ```bash
   git clone <repository-url>
   ```

2. Open the project in your favorite Integrated Development Environment (IDE).

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

The Weather Service will be up and running at `http://localhost:8080`. You can access the endpoints as described in the API section.
