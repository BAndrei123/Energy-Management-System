import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './DevicesConsumption.css';
import DropdownApp from '../../components/DropdownApp';
import LineChart from '../../components/LineChart';
import dateIcon from '../../components/assets/calendar.png';

export const DevicesConsumption = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [inputData, setInputData] = useState({
        dob: '', // Date input state
    });
    const userId = localStorage.getItem("id");

    const jwt = localStorage.getItem("jwtToken");

    
    const fetchData = async (date) => {
        setLoading(true);
        setError(null); // Reset error state
    
        try {
            const response = await axios.get(
                `http://monitoring-spring.localhost/api/DEVICE_CONSUMPTION/${date}/${userId}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            const data = response.data;
    
            // Group data by deviceIdRel and transform it
            const groupedData = data.reduce((acc, item) => {
                const hour = new Date(item.timeConsumption).toLocaleTimeString('en-US', {
                    hour: '2-digit',
                    minute: '2-digit',
                });
    
                const deviceId = item.device.deviceIdRel;
                const deviceName = `Device ${deviceId}`;
    
                // Find or create the dataset for the device
                let device = acc.find(d => d.deviceName === deviceName);
                if (!device) {
                    device = { deviceName, consumptions: [] };
                    acc.push(device);
                }
    
                // Add the consumption data
                device.consumptions.push({ hour, consumption: item.consumption });
    
                return acc;
            }, []);
    
            setChartData(groupedData);
        } catch (error) {
            setError('Failed to fetch data. Please try again later.');
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    // Update chart data when the date changes
    const handleDateChange = (event) => {
        const { name, value } = event.target;
        setInputData({ ...inputData, [name]: value });
        if (value) {
            fetchData(value); // Fetch data for the selected date
        }
    };

    const renderInputField = (label, name, value, type = 'text', icon) => (
        <div className="input">
            <img src={icon} alt="" />
            <input
                type={type}
                placeholder={label}
                name={name}
                value={value}
                onChange={handleDateChange}
            />
        </div>
    );

    return (
        <div className="container">
            <div className="dropdown-consumption-years">
                {renderInputField('Select Date', 'dob', inputData.dob, 'date', dateIcon)}
            </div>

            {error && <p className="error">{error}</p>}
            {!loading && !error  && (
                <div className="chart-container">
                    <LineChart className="line-chart" data={chartData} />
                </div>
            )}
        </div>
    );
};
