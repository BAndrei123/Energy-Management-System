import './App.css';
import { Navbar } from './components/Navbar';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { LoginSignup } from './pages/signuplogin/LoginSignup';
import { Home } from './pages/Home';
import { UserDetailsSetUp } from './pages/userdetails/UserDetailsSetUp';
import { UserDetails } from './pages/userdetails/UserDetails';
import DevicesUsers from './pages/devices/DevicesUsers';
import { AdminDetails } from './pages/admindetails/AdminDetails';
import PrivateRoute from './utils/PrivateRoutes';  // Import PrivateRoute
import UsersAdmin from './pages/users/UsersAdmin';
import DevicesAdmin from './pages/devices/DevicesAdmin';
import { WebSocketProvider } from './utils/WebSocketContext';
import { DevicesConsumption } from './pages/devices/DevicesConsumption';
import { ChatAdmin } from './pages/chat/ChatAdmin';
import { ChatUser } from './pages/chat/ChatUser';
function App() {
  return (
    <>
    <WebSocketProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<Home />} />
          
          {/* Public Routes */}
          <Route path='/signup' element={<LoginSignup />} />
          <Route path='/setupUser' element={<UserDetailsSetUp />} />

          {/* Protected Routes */}
          <Route element={<PrivateRoute allowedRoles={['USER']} />}>
            <Route path='/userDetails' element={<UserDetails />} />
            <Route path='/devices' element={<DevicesUsers />}
            />
            <Route path='/devicesConsumption' element={<DevicesConsumption />}
            />
            <Route path='/chat' element={<ChatUser/>}
            />
          </Route>

          {/* Admin Protected Routes */}
          <Route element={<PrivateRoute allowedRoles={['ADMIN']} />}>
            <Route path='/adminDetails' element={<AdminDetails />} />
            <Route path='/usersAdmin' element={<UsersAdmin/>}/>
            <Route path='/devicesAdmin' element={<DevicesAdmin/>}/>
            <Route path='/chatAdmin' element={<ChatAdmin/>}
            />
          </Route>
        </Routes>
      </BrowserRouter>
      </WebSocketProvider>
    </>
  );
}

export default App;
