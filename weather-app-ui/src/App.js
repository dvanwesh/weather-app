import React, { useState, useEffect } from "react";
import axios from "axios";
import SearchInput from "./SearchInput";
import UnitSelector from "./UnitSelector";
import WeatherCard from "./WeatherCard";
import DayList from "./DayList";

const App = () => {
  const [weatherData, setWeatherData] = useState(null);
  const [dailyWeatherData, setDailyWeatherData] = useState([]);
  const [city, setCity] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [searchType, setSearchType] = useState("");
  const [units, setUnits] = useState("");
  const [selectedDay, setSelectedDay] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchData() {
      const storedSearchType = localStorage.getItem("searchType") || searchType;
      const storedCity = localStorage.getItem("city") || city;
      const storedZipcode = localStorage.getItem("zipcode") || zipcode;
      const storedUnits = localStorage.getItem("units") || units;

      if (!storedSearchType || (!storedCity && !storedZipcode)) {
        return;
      }

      const queryParams = {};

      if (storedUnits) {
        queryParams.units = storedUnits;
      }

      if (storedSearchType === "city") {
        queryParams.city = storedCity;
      } else if (storedSearchType === "zipcode") {
        queryParams.zipcode = storedZipcode;
      } else {
        return;
      }

      // Build the query string from the queryParams object
      const queryString = Object.keys(queryParams)
        .map((key) => `${key}=${encodeURIComponent(queryParams[key])}`)
        .join("&");

      // Check if the data is already cached
      const cachedData = localStorage.getItem(queryString);

      if (cachedData) {
        // If cached data exists and is not expired, use it
        const { timestamp, data } = JSON.parse(cachedData);
        const now = new Date().getTime();
        if (now - timestamp <= 300000) {
          // Update the selectedDay data with the new units
          if (selectedDay) {
            const updatedSelectedDay = data.dailyWeatherData.find(
              (day) => day.dateTime === selectedDay.dateTime
            );
            if (updatedSelectedDay) {
              setSelectedDay(updatedSelectedDay);
            }
          }
          // 5 minutes in milliseconds
          setWeatherData(data.weatherData);
          setDailyWeatherData(data.dailyWeatherData);
          setUnits(storedUnits);
          setError(null);
          return;
        }
      }
      try {
        // Fetch the weather data
        const [currentWeatherResponse, dailyWeatherResponse] =
          await Promise.all([
            axios.get(`http://localhost:8080/weather/current?${queryString}`),
            axios.get(`http://localhost:8080/weather/daily?${queryString}`),
          ]);

        // Update the selectedDay data with the new units
        if (selectedDay) {
          const updatedSelectedDay = dailyWeatherResponse.data.data.find(
            (day) => day.dateTime === selectedDay.dateTime
          );
          if (updatedSelectedDay) {
            setSelectedDay(updatedSelectedDay);
          }
        }

        // Cache the data with the current timestamp
        const dataToCache = {
          timestamp: new Date().getTime(),
          data: {
            weatherData: currentWeatherResponse.data,
            dailyWeatherData: dailyWeatherResponse.data.data,
          },
        };

        localStorage.setItem(queryString, JSON.stringify(dataToCache));
        setWeatherData(currentWeatherResponse.data);
        setDailyWeatherData(dailyWeatherResponse.data.data);
        setUnits(storedUnits);
        setError(null);
      } catch (error) {
        if (error.response && error.response.status === 400) {
          // Check if the error is a 400 Bad Request
          setError(error.response.data.message); // Set error message from the API response
        } else {
          console.error("Error fetching weather data:", error);
          setError("Error fetching weather data. Please try again."); // Set a generic error message
        }
      }
    }

    fetchData();
  }, [searchType, city, zipcode, units, selectedDay]);

  const handleSearchChange = (searchType, input) => {
    setSearchType(searchType);
    if (searchType === "city") {
      setCity(input);
      localStorage.setItem("city", input);
    } else {
      setZipcode(input);
      localStorage.setItem("zipcode", input);
    }
    localStorage.setItem("searchType", searchType);
  };

  const handleUnitsChange = (units) => {
    setUnits(units);
    localStorage.setItem("units", units);
  };

  const handleDayClick = (day) => {
    setSelectedDay(day);
  };

  return (
    <div>
      <SearchInput onSubmit={handleSearchChange} />
      {error && (
        <div className="text-red-600 font-bold mt-4 error-message">{error}</div>
      )}
      <div>
        <UnitSelector onSubmit={handleUnitsChange} />
        <h1 className="text-3xl font-bold text-blue-600">Current Weather</h1>
        {weatherData && (
          <ul className="list-disc mt-4">
            <li>City: {weatherData.city}</li>
            <li>Day: {weatherData.dateTime}</li>
            <li>Status: {weatherData.description}</li>
            <li>
              Temperature: {weatherData.temperature}
              {units === "I" ? "°F" : "°C"}
            </li>
            <li>Pressure: {weatherData.pressure}mb</li>
            <li>Humidity: {weatherData.humidity}%</li>
            <li>
              Wind Speed: {weatherData.windSpeed}{" "}
              {units === "I" ? "mph" : "m/s"} {weatherData.windDirectionFull}
            </li>
            <li>
              Wind Gusts: {weatherData.windGusts}{" "}
              {units === "I" ? "mph" : "m/s"}
            </li>
          </ul>
        )}
      </div>

      <DayList
        dailyWeatherData={dailyWeatherData}
        units={units}
        selectedDay={selectedDay}
        onDayClick={handleDayClick}
      />
      {selectedDay && <WeatherCard day={selectedDay} units={units} />}
    </div>
  );
};

export default App;
