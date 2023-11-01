import React, { useState } from "react";

const UnitSelector = ({ onSubmit }) => {
  const [units, setUnits] = useState("");

  const handleUnitChange = (event) => {
    setUnits(event.target.value);
    onSubmit(event.target.value);
  };

  return (
    <div className="flex items-center justify-center space-x-4">
      <input
        type="radio"
        name="units"
        id="units-metric"
        value="M"
        checked={units === "M"}
        onChange={handleUnitChange}
      />
      <label htmlFor="units-metric">Metric</label>

      <input
        type="radio"
        name="units"
        id="units-imperial"
        value="I"
        checked={units === "I"}
        onChange={handleUnitChange}
      />
      <label htmlFor="units-imperial">Imperial</label>
    </div>
  );
};

export default UnitSelector;
