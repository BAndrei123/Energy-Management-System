import React, {useState} from 'react'
import { serviceDropdown } from './NavItems'
import { Link } from 'react-router-dom'
import "./Dropdown.css"
export const Dropdown = () => {
    const [dropdown,setDropdown] = useState(false);

  return (
    <>
    <ul className={dropdown? "services-submenu clicked":'services-submenu'} onClick={()=>setDropdown(!dropdown)}>
        {
            serviceDropdown.map(item =>{
                return(
                    <li key ={item.id}>
                        <Link  to={item.path} className={item.cName} element={item.element}>
                            {item.title}
                        </Link>
                    </li>
                )
            })
        }
    </ul>
    </>
  )
}
