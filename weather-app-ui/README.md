# Weather App UI

https://github.com/dvanwesh/weather-app/assets/3809622/28bd9ca2-e36e-4e91-b5b1-0cbf74487ce4

Weather App UI is a simple React project that provides a user interface for fetching and displaying current weather and a ten-day forecast. The application has the following components:

- **SearchInput.js**: This component displays a search input field where users can enter a city name or zip code to search for weather information.

- **App.js**: The main component of the application that fetches current weather and a ten-day forecast from a backend service called `weatherbit-service` and displays the data. It also provides the functionality to toggle between units (metrics and Imperial).

- **UnitSelector.js**: This component allows users to toggle between units, switching between metrics and Imperial units for temperature, wind speed, and wind gusts.

- **DayList.js**: This component displays the ten-day weather forecast. Users can click on a specific day to view detailed weather information for that day.

- **WeatherCard.js**: This component displays the selected day's weather information, including details such as temperature, pressure, humidity, wind speed, and wind gusts.

## Usage

To use the Weather App UI, follow these steps:

1. Clone this repository to your local machine.

   ```
   git clone https://github.com/dvanwesh/weather-app-ui.git
   ```

2. Navigate to the project directory.

   ```
   cd weather-app-ui
   ```

3. Install the project dependencies.

   ```
   npm install
   ```

4. Start the development server.

   ```
   npm start
   ```

5. Open your web browser and visit `http://localhost:3000` to use the Weather App UI.

## Features

- Search for weather information by entering a city name or zip code.
- View the current weather conditions, including city, date, status, temperature, pressure, humidity, wind speed, and wind gusts.
- Toggle between units (metrics and Imperial) for temperature, wind speed, and wind gusts.
- View a ten-day weather forecast.
- Click on a specific day to see detailed weather information using the WeatherCard component.

## Caching with `localStorage`

The Weather App UI uses `localStorage` to cache search parameters, such as city, zipcode, units, and selectedDay. This cache allows the app to remember your preferences between sessions. Data in `localStorage` remains active indefinitely, but it can be manually cleared by the user.

## Error Handling

The application includes error handling for 400 Bad Requests. If the backend service responds with a 400 error, the UI will display an error message with details about the issue. The error message is styled to stand out and provide a clear indication of what went wrong.
