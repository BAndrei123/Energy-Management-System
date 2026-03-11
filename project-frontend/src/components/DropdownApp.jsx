// Dropdown.js
import React, { useState } from 'react';
import { ArrowDropDown } from '@mui/icons-material';
import { ArrowDropUp } from '@mui/icons-material';
import './DropdownApp.css';

const DropdownApp = ({ options, selectedOption, onOptionSelect }) => {
  const [isActive, setIsActive] = useState(false);

  const handleToggle = () => {
    setIsActive(!isActive);
  };

  const handleOptionClick = (option) => {
    onOptionSelect(option);
    setIsActive(false);
  };

  return (
    <div className="dropdown">
      <div className="dropdown-btn" onClick={handleToggle}>
        {selectedOption}
        {!isActive ? <ArrowDropDown /> : <ArrowDropUp />}
      </div>
      {isActive && (
        <div className="dropdown-content">
          {options.map((option) => (
            <div
              key={option}
              onClick={() => handleOptionClick(option)}
              className="dropdown-item"
            >
              {option}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default DropdownApp;
