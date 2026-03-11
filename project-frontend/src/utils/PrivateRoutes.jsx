import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const PrivateRoutes = ({ allowedRoles }) => {
    const token = localStorage.getItem("jwtToken");
    const role = localStorage.getItem("role");

    const isLoggedIn = token !== null;

    if (!isLoggedIn) {
        // If user is not logged in, redirect to the login/signup page
        return <Navigate to="/signup" replace />;
    }

    if (!allowedRoles.includes(role)) {
        alert("Unauthorized")
        // If user is logged in but does not have the required role, redirect to an appropriate page
        return <Navigate to="/" replace />;  // You can set a path for unauthorized access or home page
    }

    // If user is logged in and has the correct role, render the component
    return <Outlet />;
};

export default PrivateRoutes;
