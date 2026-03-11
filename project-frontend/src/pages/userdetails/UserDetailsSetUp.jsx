import React, { useState, useEffect } from 'react';
import user_icon from '../../components/assets/person.png';
import phone_icon from '../../components/assets/phone-ringing.png';
import address_icon from '../../components/assets/placeholder.png';
import date_icon from '../../components/assets/calendar.png';
import postalcode_icon from '../../components/assets/mailbox.png';
import './UserDetailsSetUp.css';
import axios from 'axios';

export const UserDetailsSetUp = () => {
    const [name, setName] = useState("");
    const [phone, setPhone] = useState("");
    const [address, setAddress] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [dob, setDob] = useState("");
    const [errors, setErrors] = useState({});

    useEffect(() => {
        const timer = setTimeout(() => {
            setErrors({});
        }, 5000);
        return () => clearTimeout(timer);
    }, [errors]);

    const formatDate = (dateString) => {
        const [year, month, day] = dateString.split("-");
        return `${year}-${month}-${day}`;
    };

    async function handleSubmit(event) {
        event.preventDefault();
        try {
            const token = localStorage.getItem("jwtToken");
            const email = localStorage.getItem("email");
            const phoneNumberValue = parseInt(phone);
            const postalCodeValue = parseInt(postalCode);
            const formattedDob = formatDate(dob);

            const response = await axios.post(
                'http://user-spring.localhost/api/user/post',
                {
                    email: email,
                    phoneNumber: phoneNumberValue,
                    name: name,
                    dateOfBirth: formattedDob,
                    deliveryAddress: address,
                    postalCode: postalCodeValue
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            console.log('Response:', response.data);
            if (response.status === 200) {
                localStorage.setItem("logedin", "logedin");
                localStorage.setItem("role","USER");
                window.location.href = '/userDetails';
            }
        } catch (err) {
            console.error('Error submitting user details:', err);
            if (err.response) {
                setErrors(err.response.data);
            } else if (err.request) {
                console.error('Error request:', err.request);
            } else {
                console.error('Error message:', err.message);
            }
        }
    }

    return (
        <div className="container-in">
            <div className="header">
                <div className="text">Setup</div>
                <div className="underline"></div>
            </div>

            <div className="info">
                You have to complete with your personal info in order to continue.
            </div>

            <div className="inputs">
                <div className="input">
                    <img src={user_icon} alt="" />
                    <input
                        type="text"
                        placeholder='Name'
                        value={name}
                        onChange={(event) => setName(event.target.value)}
                    />
                    {errors.name && <div className="error">{errors.name}</div>}
                </div>
                <div className="input">
                    <img src={phone_icon} alt="" />
                    <input
                        type="text"
                        placeholder='Phone Number'
                        value={phone}
                        onChange={(event) => setPhone(event.target.value)}
                    />
                    {errors.phoneNumber && <div className="error">{errors.phoneNumber}</div>}
                </div>
                <div className="input">
                    <img src={address_icon} alt="" />
                    <input
                        type="text"
                        placeholder='Delivery Address'
                        value={address}
                        onChange={(event) => setAddress(event.target.value)}
                    />
                    {errors.deliveryAddress && <div className="error">{errors.deliveryAddress}</div>}
                </div>
                <div className="input">
                    <img src={postalcode_icon} alt="" />
                    <input
                        type="text"
                        placeholder='Postal Code'
                        value={postalCode}
                        onChange={(event) => setPostalCode(event.target.value)}
                    />
                    {errors.postalCode && <div className="error">{errors.postalCode}</div>}
                </div>
                <div className="input">
                    <img src={date_icon} alt="" />
                    <input
                        type="date"
                        placeholder='Date of Birth'
                        value={dob}
                        onChange={(event) => setDob(event.target.value)}
                    />
                    {errors.dateOfBirth && <div className="error">{errors.dateOfBirth}</div>}
                </div>
            </div>

            <div className="submit-container">
                <div className="submit" onClick={handleSubmit}>
                    Submit
                </div>
            </div>
        </div>
    );
};