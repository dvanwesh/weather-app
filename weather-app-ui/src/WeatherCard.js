import React from "react";

const WeatherCard = ({ day, units }) => {
  return (
    <div className="mt-4">
      <h1 className="text-2xl font-bold text-blue-600">
        {day.dateTime} Weather
      </h1>
      <ul className="list-disc mt-2">
        <li>Day: {day.dateTime}</li>
        <li>Status: {day.description}</li>
        <li>
          Min Temperature: {day.minTemperature}
          {units === "I" ? "°F" : "°C"}
        </li>
        <li>
          Max Temperature: {day.maxTemperature}
          {units === "I" ? "°F" : "°C"}
        </li>
        <li>Pressure: {day.pressure}mb</li>
        <li>Humidity: {day.humidity}%</li>
        <li>
          Wind Speed: {day.windSpeed} {units === "I" ? "mph" : "m/s"}{" "}
          {day.windDirectionFull}
        </li>
        <li>
          Wind Gusts: {day.windGusts} {units === "I" ? "mph" : "m/s"}
        </li>
      </ul>
    </div>
  );
};

export default WeatherCard;
