import { useState, useEffect } from "react";
import React from 'react';
import user_icon from '../../components/assets/person.png';
import email_icon from '../../components/assets/email.png';
import password_icon from '../../components/assets/password.png';
import axios from "axios";
import './LoginSignup.css';


export const LoginSignup = () => {
    const [action, setAction] = useState("Login");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({});
    const [userDetails, setUserDetails] = useState([]);
    // Function to remove errors after a certain period of time
    useEffect(() => {
        const timer = setTimeout(() => {
            setErrors({});
        }, 5000); // Set the timeout duration in milliseconds (5 seconds in this example)
        
        return () => clearTimeout(timer); // Cleanup function to clear the timer
    }, [errors]); // Re-run effect whenever errors state changes

    async function save(event) {
        event.preventDefault();
        try {
            const response = await axios.post("http://user-spring.localhost/api/auth/sign-up", {
                username: name,
                email: email,
                password: password,
                roles: "USER"
            });

            console.log(response.data);
            
            alert("User registered successfully");
            setErrors({});
            setPassword("");
            setAction("Login")

        } catch (err) {
            if (err.response && err.response.data) {
                setErrors(err.response.data);
            } else {
                alert("User registration failed");
            }
        }
    }

    async function login(event) {
        event.preventDefault();
        try {
            const response = await axios.post("http://user-spring.localhost/api/auth/sign-in", {
                username: name,
                password: password,
            });
            // const role = await axios.get(`http://localhost:8080/api/user_role/username/get/${name}`,{
            //     headers: {
            //         Authorization: `Bearer ${response.data.token}`
            //     }
            // });
            console.log(response.data);
            if (response.status === 200) {
                // Redirect to /home
                const username = response.data.username;
                localStorage.setItem("logedin", "logedin");
                localStorage.setItem("jwtToken", response.data.token);
                localStorage.setItem("email",response.data.email);
                localStorage.setItem("username",response.data.username);
                localStorage.setItem("id",response.data.id);
                if(response.data.roles[0] === "ADMIN")
                {
                 localStorage.setItem("role","ADMIN");
                 window.location.href = "/adminDetails";   
                }else{
                await fetchUserDetails(event, username,response.data.token);
                }
            } 
    
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setErrors({ credentials: "Invalid credentials" });
            } else {
                alert("An error occurred while logging in");
                console.log(err);
            }
        }
    }
    async function fetchUserDetails(event, username,token){
        event.preventDefault();
        try{
            const response = await axios.get(`http://user-spring.localhost/api/user/user_name/get/${username}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

            if(response.status===200){
                localStorage.setItem("role","USER");
                window.location.href="/userDetails";

            }
        }catch(err){
            if(err.response && err.response.status === 404){
                console.log("no data");
                window.location.href = "/setupUser";
            }
            else{
                alert("An error occurred while fetching user's data");
            }
        }
    }

    return (
        <div className="container-in">
            <div className="header">
                <div className="text">{action}</div>
                <div className="underline"></div>
            </div>
            
            <div className="inputs">
         
                {action === "Login" ? <div></div> :
                    <div className="input">
                        <img src={email_icon} alt="" />
                        <input type="email" placeholder='Email'
                               value={email}
                               onChange={(event) => setEmail(event.target.value)}
                        />
                        {errors.email && <div className="error">{errors.email}</div>}
                    </div>
                }
                <div className="input">
                    <img src={user_icon} alt="" />
                    <input type="text" placeholder='Username'
                           value={name}
                           onChange={(event) => setName(event.target.value)}
                    />
                    {errors.username && <div className="error">{errors.username}</div>}
                </div>
                <div className="input">
                    <img src={password_icon} alt="" />
                    <input type="password" placeholder='Password'
                           value={password}
                           onChange={(event) => setPassword(event.target.value)}
                    />
                    {errors.password && <div className="error">{errors.password}</div>}
                </div>
            </div>
            {errors.credentials && <div className="invalid-credentials">{errors.credentials}</div>}
            <div className="submit-container">
                <div className={action === "Login" ? "submit gray" : "submit"}
                     onClick={action === "Sign Up" ? save : () => setAction("Sign Up")}>
                    Sign Up
                </div>
                <div className={action === "Sign Up" ? "submit gray" : "submit"}
                     onClick={action === "Login" ? login : () => setAction("Login")}>
                    Login
                </div>
            </div>
        </div>
    )
}
