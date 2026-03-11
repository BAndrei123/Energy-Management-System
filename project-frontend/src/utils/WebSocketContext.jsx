import React, { createContext, useContext, useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const WebSocketContext = createContext();

export const useWebSocket = () => {
  return useContext(WebSocketContext);
};

export const WebSocketProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);
  const [notifications, setNotifications] = useState([]); // Store notifications here
  const id = localStorage.getItem("id");
  useEffect(() => {
    // Create the STOMP client
    const client = new Client({
      webSocketFactory: () => new SockJS('http://monitoring-spring.localhost/ws'),
      debug: (str) => console.log(str), // Debug logs
      reconnectDelay: 5000, // Auto-reconnect every 5 seconds
    });

    client.onConnect = () => {
      console.log('Connected to WebSocket');

      // Subscribe to notifications
      client.subscribe(`/topic/notifications/user-${id}`, (message) => {
        console.log('Received notification:', message.body);
        
        // Trigger an alert with the message body
        alert(`New Notification: ${message.body}`);
        
        // Optionally store the notification in the state if you want to render it
        setNotifications((prevNotifications) => [
          ...prevNotifications,
          message.body,
        ]);
      });
    };

    client.onStompError = (frame) => {
      console.error('Broker reported error:', frame.headers['message']);
      console.error('Additional details:', frame.body);
    };

    client.activate();
    setStompClient(client);

    // Cleanup on unmount
    return () => {
      client.deactivate();
    };
  }, []);

  return (
    <WebSocketContext.Provider value={{ stompClient, notifications }}>
      {children}
    </WebSocketContext.Provider>
  );
};
