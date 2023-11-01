import React, { useState } from "react";

const SearchInput = ({ onSubmit }) => {
  const [input, setInput] = useState("");

  const isZipcode = (value) => {
    return /^\d+$/.test(value);
  };

  const handleChange = (event) => {
    setInput(event.target.value);
  };

  const handleSubmit = (event) => {
    const searchType = isZipcode(input) ? "zipcode" : "city";
    onSubmit(searchType, input);
  };

  return (
    <div class="flex items-center justify-center border-b border-gray-200 py-2">
      <input
        class="flex-1 px-4 py-2 border-none outline-none text-center bg-blue-500 text-white"
        type="text"
        placeholder="Enter a city name or zip code"
        value={input}
        onChange={handleChange}
      />
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        onClick={handleSubmit}
      >
        Search
      </button>
    </div>
  );
};

export default SearchInput;
