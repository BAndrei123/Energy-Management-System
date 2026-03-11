import React, { useEffect, useState } from 'react';
import './DevicesAdmin.css';
import ButtonGroup from '../../components/ButtonGroup';
import DropdownApp from '../../components/DropdownApp';
import axios from 'axios';

const DevicesAdmin = () => {
    const actions = [
        { label: 'Add Device', value: 'AddDevice' },
        { label: 'Edit Device', value: 'EditDevice' },
        { label: 'Delete Device', value: 'DeleteDevice' },
    ];

    const [action, setAction] = useState("AddDevice");
    const [deviceList, setDeviceList] = useState([]);
    const [selectedDevice, setSelectedDevice] = useState('Select device');
    const [errors, setErrors] = useState({});

    const jwt = localStorage.getItem("jwtToken");

    const [deviceData, setDeviceData] = useState({
        id: "",
        name: "",
        description: "",
        address: "",
        maximumHourlyConsumption: "",
    });

    const { id, name, description, address, maximumHourlyConsumption } = deviceData;

    const handleActionSelect = (selectedAction) => setAction(selectedAction);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setDeviceData({ ...deviceData, [name]: value });
    };

    const handleError = (err) => {
        if (err.response) {
            setErrors(err.response.data);
        } else {
            console.error(err.message);
        }
    };

    const addDevice = async () => {
        try {
            const response = await axios.post(
                'http://devices-spring.localhost/api/DEVICE/post',
                {
                    name,
                    description,
                    address,
                    maximumHourlyConsumption,
                },
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            if (response.status === 200) {
                alert('Device added');
                fetchDevices();
                resetForm();
            }
        } catch (err) {
            handleError(err);
        }
    };

    const updateDevice = async () => {
        try {
            const response = await axios.put(
                `http://devices-spring.localhost/api/DEVICE/put/${id}`,
                {
                    name,
                    description,
                    address,
                    maximumHourlyConsumption,
                },
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            if (response.status === 200) {
                alert('Device updated');
                fetchDevices();
                setSelectedDevice('Select device');
                resetForm();
            }
        } catch (err) {
            handleError(err);
        }
    };
    
    const deleteDevice = async () => {
        try {
            const response = await axios.delete(
                `http://devices-spring.localhost/api/DEVICE/delete/${id}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            if (response.status === 200) {
                alert('Device deleted');
                fetchDevices();
                setSelectedDevice('Select device');
            }
        } catch (err) {
            handleError(err);
        }
    };

    const handleMakeChange = () => {
        switch (action) {
            case "AddDevice":
                addDevice();
                break;
            case "EditDevice":
                updateDevice();
                break;
            case "DeleteDevice":
                deleteDevice();
                break;
            default:
                console.log("No action selected");
        }
    };

    const fetchDevices = async () => {
        try {
            const response = await axios.get(
                'http://devices-spring.localhost/api/DEVICE/get/all',
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            const deviceNames = response.data.map((device) => device.name);
            setDeviceList(deviceNames);
        } catch (err) {
            handleError(err);
        }
    };

    const fetchDeviceDetails = async (deviceName) => {
        try {
            const response = await axios.get(
                `http://devices-spring.localhost/api/DEVICE/get/name/${deviceName}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                }
            );
    
            const { id, name, description, address, maximumHourlyConsumption } = response.data;
            setDeviceData({ id, name, description, address, maximumHourlyConsumption });
        } catch (err) {
            handleError(err);
        }
    };

    const resetForm = () => {
        setDeviceData({
            id: "",
            name: "",
            description: "",
            address: "",
            maximumHourlyConsumption: "",
        });
        setErrors({});
    };

    useEffect(() => {
        fetchDevices();
    }, []);

    useEffect(() => {
        if (selectedDevice !== 'Select device') {
            fetchDeviceDetails(selectedDevice);
        }
    }, [selectedDevice]);

    

    const renderInputField = (label, name, value, type = "text") => (
        <div className="input">
            <img alt="" />
            <input
                type={type}
                placeholder={label}
                name={name}
                value={value}
                onChange={handleInputChange}
            />
            {errors[name] && <div className="error">{errors[name]}</div>}
        </div>
    );

    return (
        <div className='inputs'>
            <ButtonGroup actions={actions} onActionSelect={handleActionSelect} defaultAction="AddDevice" />

            {action === "AddDevice" && (
                <>
                    {renderInputField('Name', 'name', name)}
                    {renderInputField('Description', 'description', description)}
                    {renderInputField('Address', 'address', address)}
                    {renderInputField('Maximum hourly energy consumption', 'maximumHourlyConsumption', maximumHourlyConsumption, "number")}
                </>
            )}

            {action === "EditDevice" && (
                <>
                    <div className="dropdown-container">
                    <DropdownApp
                        className="devices-dropdown"
                        options={deviceList}
                        selectedOption={selectedDevice}
                        onOptionSelect={setSelectedDevice}
                    />
                    </div>
                    {renderInputField('Name', 'name', name)}
                    {renderInputField('Description', 'description', description)}
                    {renderInputField('Address', 'address', address)}
                    {renderInputField('Maximum hourly energy consumption', 'maximumHourlyConsumption', maximumHourlyConsumption, "number")}
                </>
            )}

            {action === "DeleteDevice" && (
                <div className="dropdown-container">
                <DropdownApp
                    className="devices-dropdown"
                    options={deviceList}
                    selectedOption={selectedDevice}
                    onOptionSelect={setSelectedDevice}
                />
                </div>
            )}

            <div className="make-change" onClick={handleMakeChange}>
                Make change
            </div>
        </div>
    );
};

export default DevicesAdmin;
