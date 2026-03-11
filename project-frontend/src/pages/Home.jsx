import React from 'react';
import backgroundPhoto from '../components/assets/background-home.jpg';
import './Home.css';

export const Home = () => {
  return (
    <div className="home-container">
      <img src={backgroundPhoto} alt="Background" className="background-image" />
      <div className="title">
        <span className="line1">Welcome</span>
      </div>
    </div>
  );
};
