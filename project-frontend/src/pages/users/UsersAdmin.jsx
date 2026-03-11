import React, { useEffect, useState } from 'react';
import './UsersAdmin.css';
import emailIcon from '../../components/assets/email.png';
import userIcon from '../../components/assets/person.png';
import phoneIcon from '../../components/assets/phone-ringing.png';
import addressIcon from '../../components/assets/placeholder.png';
import dateIcon from '../../components/assets/calendar.png';
import postalCodeIcon from '../../components/assets/mailbox.png';
import ButtonGroup from '../../components/ButtonGroup';
import DropdownApp from '../../components/DropdownApp';
import axios from 'axios';

const UsersAdmin = () => {
    const actions = [
        { label: 'Add User', value: 'AddUser' },
        { label: 'Edit User', value: 'EditUser' },
        { label: 'Delete User', value: 'DeleteUser' },
        { label: 'Map Device to User', value: 'MapDevice' }
    ];

    const [action, setAction] = useState("AddUser");
    const [emailList, setEmailList] = useState([]);
    const [selectedEmail, setSelectedEmail] = useState('Select email');
    const [deviceList, setDeviceList] = useState([]);
    const [selectedDevice, setSelectedDevice] = useState('Select device');
    const [userData, setUserData] = useState({
        email: "",
        name: "",
        phone: "",
        address: "",
        postalCode: "",
        dob: ""
    });
    const [errors, setErrors] = useState({});
    const { email, username, name, phone, address, postalCode, dob } = userData;
    const [idEmail, setIdEmail] = useState("");
    const [deviceId, setDeviceId] = useState("");

    const handleActionSelect = (selectedAction) => setAction(selectedAction);
    

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUserData({ ...userData, [name]: value });
    };

    const formatDate = (dateString) => {
        const [year, month, day] = dateString.split("-");
        return `${year}-${month}-${day}`;
    };

    const handleError = (err) => {
        if (err.response) {
            setErrors(err.response.data);
        } else {
            console.error(err.message);
        }
    };

    async function fetchUserEmails() {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await axios.get('http://user-spring.localhost/api/user_role/get/all/USER', {
                headers: { Authorization: `Bearer ${token}` }
            });
            if (response.status === 200) {
                const emails = response.data.map((user) => user.credentials.email);
            setEmailList(emails);
            }
        } catch (err) {
            handleError(err);
        }
    }

    const fetchDevices = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await axios.get('http://devices-spring.localhost/api/DEVICE/get/all', {
                headers: { Authorization: `Bearer ${token}` }
            });
            const deviceNames = response.data.map((device) => device.name);
            setDeviceList(deviceNames);
        } catch (err) {
            handleError(err);
        }
    };

    async function fetchCredential() {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await axios.get(`http://user-spring.localhost/api/credentials/get/${selectedEmail}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            if (response.status === 200) {
                console.log(idEmail);
                setIdEmail(response.data.id);

            }
        } catch (err) {
            handleError(err);
        }
    }

    async function fetchUserDetails(){
        try{
            const token = localStorage.getItem('jwtToken');
            const response = await axios.get(`http://user-spring.localhost/api/user/get/${selectedEmail}`,{
                headers: {Authorization : `Bearer ${token}`}
            });
            if(response.status===200){
                const userData = {
                    email: response.data.credentials.email,
                    username: response.data.credentials.username,
                    name: response.data.name,
                    phone: response.data.phoneNumber,
                    address: response.data.deliveryAddress,
                    postalCode: response.data.postalCode,
                    dob: response.data.dateOfBirth
                };
    
                // Set the user data
                setUserData(userData);
                
                // Set the ID separately if needed
                setIdEmail(response.data.credentials.id);
                
            }
        }catch(err){
            handleError(err);
        }
    }

    const fetchDeviceDetails = async () => {
        try {
            const token = localStorage.getItem('jwtToken');
            const response = await axios.get(`http://devices-spring.localhost/api/DEVICE/get/name/${selectedDevice}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setDeviceId(response.data.id);
        } catch (err) {
            handleError(err);
        }
    };

    async function addUser() {
        try {
            const responseCreateAccount = await axios.post("http://user-spring.localhost/api/auth/sign-up", {
                username: username,
                email: email,
                password: "passwordUser1",
                roles: "USER"
            });
            if (responseCreateAccount.status === 200) {
                addUserDetails();
            } else {
                alert("Error occurred while creating account");
            }
        } catch (err) {
            handleError(err);
        }
    }

    async function addUserDetails() {
        try{
            const token = localStorage.getItem("jwtToken");
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
            if(response.status===200){
                alert('Account was created');
                resetForm();
                fetchUserEmails();
                
            }
            
        }catch(err){
            handleError(err);
        }
        
    }

    async function updateUser() {
        try{
            const token = localStorage.getItem("jwtToken");
            const phoneNumberValue = parseInt(phone);
            const postalCodeValue = parseInt(postalCode);
            const formattedDob = formatDate(dob);
            const response = await axios.put('http://user-spring.localhost/api/user/put',
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
            )
            if(response.status===200){
                alert('User updated');
                resetForm();
                console.log(response);
            }
        }catch(err){
            handleError(err);
        }
        
    }

    async function deleteDevicesMapedtoUser(){
        try{
            console.log('dadada');
            console.log(idEmail);
            const response = await axios.delete(`http://devices-spring.localhost/api/DEVICE_USER/delete/${idEmail}`)
            console.log(response);
            if(response.status===200){
                console.log('Deleted');
            }
        }catch(err){
            handleError(err);
        }
    }

    async function deleteUser() {
        try {
            
            const token = localStorage.getItem("jwtToken");
            const response = await axios.delete(`http://user-spring.localhost/api/credentials/delete/${selectedEmail}`, {
                headers: { Authorization: `Bearer ${token}` }
            });

          
            if (response.status === 200) {
                setSelectedEmail('Select email');
                fetchUserEmails();
                
            }
        } catch (err) {
            handleError(err);
        }
    }

    
    async function mapDeviceToUser() {
        try {
            const token = localStorage.getItem('jwtToken');
            const response = await axios.post(
                'http://devices-spring.localhost/api/DEVICE_USER/post',
                {
                    deviceId: deviceId,
                    userId: idEmail,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            if (response.status === 200) {
                alert("Device mapped to user");
            }
        } catch (err) {
            handleError(err);
        }
    }

    useEffect(() => {
        fetchUserEmails();
        fetchDevices();
    }, []);

    useEffect(() => {
        if (selectedEmail !== 'Select email') {
            fetchCredential();
            fetchUserDetails();
            console.log('dadadadadada');
        }
        if (selectedDevice !== 'Select device') {
            fetchDeviceDetails();
            console.log('dadadadadada');
        }
        console.log('dadadadadada');
    }, [selectedEmail, selectedDevice]);

    const handleMakeChange = () => {
        switch (action) {
            case "AddUser":
                addUser();
                break;
            case "EditUser":
                updateUser();
                break;
            case "DeleteUser":
                
                deleteUser();
                break;
            case "MapDevice":
                mapDeviceToUser();
                break;
            default:
                console.log("No action selected");
        }
    };

    const renderInputField = (label, name, value, type = "text", icon, disabledValue) => (
        <div className="input">
            <img src={icon} alt="" />
            <input
                type={type}
                placeholder={label}
                name={name}
                value={value}
                onChange={handleInputChange}
                disabled= {disabledValue}
            />
            {errors[name] && <div className="error">{errors[name]}</div>}
        </div>
    );

    const resetForm = () => {
        setUserData({
            email: "",
            username: "",
            name: "",
            phone: "",
            address: "",
            postalCode: "",
            dob: ""
        });
        setSelectedEmail('Select email');
        setSelectedDevice('Select device');
        setErrors({});
        
    };

    return (
        <div className='inputs'>
            <ButtonGroup actions={actions} onActionSelect={handleActionSelect} defaultAction="AddUser" />
            {action === "AddUser" && (
                <>
                    {renderInputField('Email', 'email', email, "text", emailIcon,false)}
                    {renderInputField('Username', 'username', username, "text", userIcon,false)}
                    {renderInputField('Name', 'name', name, "text", userIcon,false)}
                    {renderInputField('Phone Number', 'phone', phone, "text", phoneIcon,false)}
                    {renderInputField('Delivery Address', 'address', address, "text", addressIcon,false)}
                    {renderInputField('Postal Code', 'postalCode', postalCode, "text", postalCodeIcon,false)}
                    {renderInputField('Date of Birth', 'dob', dob, "date", dateIcon,false)}
                </>
            )}
            {action === "EditUser" && (
                <>
                    <div className="dropdown-container">
                        <DropdownApp
                            className="emails-dropdown"
                            options={emailList}
                            selectedOption={selectedEmail}
                            onOptionSelect={setSelectedEmail}
                        />
                    </div>
                    {renderInputField('Email', 'email', email, "text", emailIcon,true)}
                    {renderInputField('Username', 'username', username, "text", userIcon,true)}
                    {renderInputField('Name', 'name', name, "text", userIcon,false)}
                    {renderInputField('Phone Number', 'phone', phone, "text", phoneIcon,false)}
                    {renderInputField('Delivery Address', 'address', address, "text", addressIcon,false)}
                    {renderInputField('Postal Code', 'postalCode', postalCode, "text", postalCodeIcon,false)}
                    {renderInputField('Date of Birth', 'dob', dob, "date", dateIcon,false)}
                </>
            )}
            {action === "DeleteUser" && (
                <div className="dropdown-container">
                    <DropdownApp
                        className="emails-dropdown"
                        options={emailList}
                        selectedOption={selectedEmail}
                        onOptionSelect={setSelectedEmail}
                    />
                </div>
            )}
            {action === "MapDevice" && (
                <>
                    <div className="dropdown-container">
                        <DropdownApp
                            className="emails-dropdown"
                            options={emailList}
                            selectedOption={selectedEmail}
                            onOptionSelect={setSelectedEmail}
                        />
                    </div>
                    <div className="dropdown-container">
                        <DropdownApp
                            className="devices-dropdown"
                            options={deviceList}
                            selectedOption={selectedDevice}
                            onOptionSelect={setSelectedDevice}
                        />
                    </div>
                </>
            )}
            <div className="make-change" onClick={handleMakeChange}>
                Make change
            </div>
        </div>
    );
};

export default UsersAdmin;
