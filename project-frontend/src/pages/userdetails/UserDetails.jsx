import React, { useEffect, useState } from 'react';
import './UserDetails.css';
import axios from 'axios';

export const UserDetails = () => {
    const email = localStorage.getItem("email");
    const username = localStorage.getItem("username");
    const token = localStorage.getItem("jwtToken");

    const [userDetails, setUserDetails] = useState({
        email: email || "",
        name: "",
        phoneNumber: "",
        dateOfBirth: "",
        deliveryAddress: "",
        postalCode: ""
    });

    const [isEditing, setIsEditing] = useState(false);

    const formatDate = (dateString) => {
        const [year, month, day] = dateString.split("-");
        return `${year}-${month}-${day}`;
    };

    

    const fetchUserDetails = async (email, token) => {
        try {
            const response = await axios.get(`http://user-spring.localhost/api/user/get/${email}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (response.status === 200) {
                setUserDetails(prevDetails => ({
                    ...response.data,
                    email: prevDetails.email // Preserve the email from local storage
                }));
                localStorage.setItem("userDetails", JSON.stringify(response.data));
            }
        } catch (err) {
            alert("An error occurred while fetching user's data");
        }
    };

    useEffect(() => {
        const storedDetails = localStorage.getItem("userDetails");
        
        
        if (storedDetails) {
            setUserDetails(JSON.parse(storedDetails));
        } else if (email && token) {
            fetchUserDetails(email, token);
        }
    }, [email, token]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserDetails(prevDetails => ({
            ...prevDetails,
            [name]: value
        }));
    };

    const toggleEdit = async () => {
        if (isEditing) {
            try {
                
                const response = await axios.put(
                    'http://user-spring.localhost/api/user/put',
                    {
                         name: userDetails.name,
                        phoneNumber: userDetails.phoneNumber,
                        dateOfBirth: userDetails.dateOfBirth,
                        deliveryAddress: userDetails.deliveryAddress,
                        postalCode: userDetails.postalCode,
                        email: userDetails.credentials.email
                    },
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );
                if (response.status === 200) {
                    localStorage.setItem("userDetails", JSON.stringify(userDetails));
                }
            } catch (err) {
                alert('An error occurred while updating user details');
            }
        }
        setIsEditing(!isEditing);
    };

    const handleLogout = () => {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("email");
        localStorage.removeItem("userDetails");
        localStorage.removeItem("logedin");
        localStorage.removeItem("role");
        window.location.href = "/signUp";
    };

    return (
        <div className="user-details-container">
            <div className="header">
                <h2>Personal info</h2>
                <div className="underline"></div>
            </div>
            <div className="user-detail"><strong>Email:</strong>{ email}</div>
            <div className="user-detail"><strong>Username:</strong> {username}</div>
            <div className="user-detail">
                <strong>Name:</strong>
                {isEditing ? (
                    <input type="text" name="name" value={userDetails.name} onChange={handleChange} />
                ) : (
                    userDetails.name
                )}
            </div>
            <div className="user-detail">
                <strong>Phone Number:</strong>
                {isEditing ? (
                    <input type="text" name="phoneNumber" value={userDetails.phoneNumber} onChange={handleChange} />
                ) : (
                    userDetails.phoneNumber
                )}
            </div>
            <div className="user-detail">
                <strong>Delivery Address:</strong>
                {isEditing ? (
                    <input type="text" name="deliveryAddress" value={userDetails.deliveryAddress} onChange={handleChange} />
                ) : (
                    userDetails.deliveryAddress
                )}
            </div>
            <div className="user-detail">
                <strong>Postal Code:</strong>
                {isEditing ? (
                    <input type="text" name="postalCode" value={userDetails.postalCode} onChange={handleChange} />
                ) : (
                    userDetails.postalCode
                )}
            </div>
            <div className="user-detail">
                <strong>Date of Birth:</strong>
                {isEditing ? (
                    <input type="date" name="dateOfBirth" value={userDetails.dateOfBirth} onChange={handleChange} />
                ) : (
                    formatDate(userDetails.dateOfBirth)
                )}
            </div>
            <div className="button-container">
                <button onClick={toggleEdit} className="action-button">
                    {isEditing ? "Save" : "Edit"}
                </button>
                <button onClick={handleLogout} className="action-button logout-button">
                    Logout
                </button>
            </div>
        </div>
    );
};
