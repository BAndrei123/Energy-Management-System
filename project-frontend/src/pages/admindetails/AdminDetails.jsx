import React from 'react';
import './AdminDetails.css';

export const AdminDetails = () => {
    const email = localStorage.getItem("email");

    const handleLogout = () => {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("email");
        localStorage.removeItem("logedin");
        localStorage.removeItem("role");
        window.location.href = "/signUp";
    };

    return (
        <div className="admin-details-container">
            <div className="header">
                <h2>Personal Info</h2>
                <div className="underline"></div>
            </div>
            <div className="admin-detail"><strong>Email:</strong>{ email }</div>
            <div className="button-container">
                <button onClick={handleLogout} className="action-button logout-button">
                    Logout
                </button>
            </div>
        </div>
    );
};
