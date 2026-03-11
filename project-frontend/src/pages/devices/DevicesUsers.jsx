import React, { useMemo, useState, useEffect } from 'react'
import './DevicesUsers.css'
import DataTable from '../../components/DataTable';
import axios from 'axios';

const DevicesUsers = () => {
    
    const [devicesData, setDevicesData] = useState([]);

    const [currentPage, setCurrentPage] = useState(0);
    const [lastPage, setLastPage] = useState(true);
   
    const jwt = localStorage.getItem("jwtToken");
  

    const fetchDevices = async (page) => {
        try {
            const userId = localStorage.getItem("id");
            const response = await axios.get(
                `http://devices-spring.localhost/api/DEVICE_USER/get/${userId}/${page}`, 
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
            console.log(response);
            setDevicesData(response.data.content);
            setLastPage(response.data.last);
        } catch (err) {
            console.error('Error fetching devices', err);
        }
    };

    

    const userId = localStorage.getItem("id");
    console.log('User ID:', userId);
    useEffect(() => {
        fetchDevices(currentPage);
      }, []);

    const handlePageChange = (page) => {
        if (page >= 0) {
            setCurrentPage(page);
            fetchDevices(page);
        }
    };
    const columns = useMemo(() => [
        {
          Header: "Name",
          accessor: "device.name", // Accessing name inside device object
        },
        {
          Header: "Description",
          accessor: "device.description", // Accessing description inside device object
        },
        {
          Header: "Address",
          accessor: "device.address", // Accessing address inside device object
        },
        {
          Header: "Max Consumption",
          accessor: "device.maximumHourlyConsumption", // Accessing max consumption
        }
        
      ], []);


    return (
        <div >

            

           
            <DataTable columns={columns} data={devicesData}/>
            
            <div className="user-devices-pagination">
                <button
                    className="user-devices-page-button"
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                >
                    Previous
                </button>
                <span>Page {currentPage + 1}</span>
                <button
                    className="user-devices-page-button"
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={lastPage === true}
                >
                    Next
                </button>
            </div>
           
          
        </div>
    )
}

export default DevicesUsers