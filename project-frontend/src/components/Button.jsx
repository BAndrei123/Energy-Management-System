import React from 'react';
import { Link } from 'react-router-dom';
import './Button.css';

export const Button = () => {
  // Retrieve data from localStorage
  const lockedIn = localStorage.getItem('logedin');
  const role = localStorage.getItem('role');
  const to = role ==="ADMIN"? 'adminDetails':'userDetails';

  return (
    <Link to={lockedIn ? to : 'signup'}>
      <button className="btn">
        {lockedIn ? "Account" : 'Sign up'}
      </button>
    </Link>
  );
};
