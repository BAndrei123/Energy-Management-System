import React, {useState} from 'react'
import { Link } from 'react-router-dom'

import "./Navbar.css";
import { navItems } from './NavItems';

import { Button } from './Button';



export const Navbar = () => {
  

  return (
    <>
    <nav className='navbar'>
        <Link to="/" className='navbar-logo'>
        </Link>
        <ul className='nav-items'>
          {navItems.map(item=>{
            
            return(
              <li key ={item.id} className={item.cName}>
                <Link to={item.path}>
                  {item.title}
                </Link>
              </li>
              )
          })}
        </ul>
        <Button/>
    </nav>
   
    </>
  )
}
