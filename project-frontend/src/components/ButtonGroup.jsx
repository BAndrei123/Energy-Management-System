import React, { useState } from 'react';
import './ButtonGroup.css'; // Style the buttons here

const ButtonGroup = ({ actions, onActionSelect, defaultAction }) => {
    const [selectedAction, setSelectedAction] = useState(defaultAction || '');

    const handleButtonClick = (action) => {
        setSelectedAction(action);
        onActionSelect(action); // Callback to notify the parent which action is selected
    };

    return (
        <div className="buttons">
            <div className="button-container">
                {actions.map((action) => (
                    <div
                        key={action.value}
                        className={`button ${selectedAction === action.value ? '' : 'gray'}`}
                        onClick={() => handleButtonClick(action.value)}
                    >
                        {action.label}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ButtonGroup;
