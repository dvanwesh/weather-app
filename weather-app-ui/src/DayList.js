import React from "react";
import { ListGroup, ListGroupItem } from "react-bootstrap";

const DayList = ({ dailyWeatherData, units, selectedDay, onDayClick }) => {
  return (
    <div>
      <h1 className="text-3xl font-bold text-blue-600">Weather forecast</h1>
      <ListGroup className="flex flex-col items-center justify-center">
        <div className="flex flex-row space-x-4">
          {dailyWeatherData.map((day, index) => (
            <ListGroupItem
              key={index}
              className={`bg-gray-${
                index % 2 === 0 ? "100" : "200"
              } p-4 rounded shadow`}
              onClick={() => onDayClick(day)}
              style={{ cursor: "pointer" }}
            >
              <p
                className={`text-sm font-medium ${
                  selectedDay === day ? "text-blue-600" : "text-gray-700"
                }`}
              >
                {day.dateTime}
              </p>
              <p className="text-xs text-gray-500">{day.description}</p>
              <p className="text-xs text-gray-500">
                Min: {day.minTemperature}
                {units === "I" ? "°F" : "°C"} Max: {day.maxTemperature}
                {units === "I" ? "°F" : "°C"}
              </p>
            </ListGroupItem>
          ))}
        </div>
      </ListGroup>
    </div>
  );
};

export default DayList;
