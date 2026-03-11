import React, { useState, useEffect } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { webSocketService } from './WebSocketService';
import './ChatAdmin.css';

export const ChatAdmin = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [allMessages, setAllMessages] = useState({});
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [typingStatus, setTypingStatus] = useState({});

  useEffect(() => {
    // Load stored users and messages from session storage
    const storedUsers = JSON.parse(sessionStorage.getItem('users')) || [];
    const storedMessages = JSON.parse(sessionStorage.getItem('messages')) || {};
    setUsers(storedUsers);
    setAllMessages(storedMessages);

    // Connect to WebSocket and subscribe to channels
    webSocketService.connect(
      (newMessage) => {
        handleIncomingMessage(newMessage);
      },
      () => {
        webSocketService.subscribe('/user/admin/private', (reply) => {
          handleIncomingMessage(reply);
        });
      }
    );

    return () => {
      webSocketService.disconnect();
    };
  }, []);

  const saveMessagesToSession = (updatedMessages) => {
    sessionStorage.setItem('messages', JSON.stringify(updatedMessages));
    setAllMessages(updatedMessages);
  };
  useEffect(() => {
    if (selectedUser) {
      // Load messages for the selected user
      const userMessages = allMessages[selectedUser] || [];
      setMessages(userMessages);

      const seenNotification = {
        senderName: 'admin',
        receiverName: selectedUser,
        status: 'SEEN',
        date: new Date().toISOString(),
      };
      webSocketService.send('/app/seen', seenNotification);
    
    }
  }, [selectedUser, allMessages]);

  const handleIncomingMessage = (message) => {
    const sender = message.senderName;
  
    // Update the users list to include the sender if not already present
    setUsers((prevUsers) => {
      if (!prevUsers.includes(sender)) {
        const updatedUsers = [...prevUsers, sender];
        sessionStorage.setItem('users', JSON.stringify(updatedUsers));
        return updatedUsers;
      }
      return prevUsers;
    });
  
    if (message.status === 'SEEN') {
      setAllMessages((prevMessages) => {
        const updatedMessages = {
          ...prevMessages,
          [sender]: (prevMessages[sender] || []).map((msg) => {
            if (msg.senderName === 'admin') {
              return { ...msg, seen: true };
            }
            return msg;
          }),
        };
  
        // Save the updated messages to sessionStorage
        sessionStorage.setItem('messages', JSON.stringify(updatedMessages));
  
        // If the sender is the currently selected user, update the chat window
        if (sender === selectedUser) {
          setMessages(updatedMessages[sender]);
        }
  
        return updatedMessages;
      });
  
      return;
    }
  
    if (message.status === 'TYPING') {
      setTypingStatus((prevStatus) => ({
        ...prevStatus,
        [sender]: true,
      }));
  
      setTimeout(() => {
        setTypingStatus((prevStatus) => ({
          ...prevStatus,
          [sender]: false,
        }));
      }, 3000);
  
      return;
    }
  
    // Handle regular messages
    setAllMessages((prevMessages) => {
      const updatedMessages = {
        ...prevMessages,
        [sender]: [...(prevMessages[sender] || []), message],
      };
  
      // Save the updated messages to sessionStorage
      sessionStorage.setItem('messages', JSON.stringify(updatedMessages));
  
      // If the sender is the currently selected user, update the chat window
      if (sender === selectedUser) {
        setMessages(updatedMessages[sender]);
      }
  
      return updatedMessages;
    });
  
    // Notify admin of a new message if not in the chat with the sender
    if (sender !== selectedUser) {
      toast.info(`New message from ${sender}: ${message.message}`, {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });
    }
  };
  

  const sendMessage = () => {
    if (message.trim() && selectedUser) {
      const newMessage = {
        senderName: 'admin',
        receiverName: selectedUser,
        message,
        date: new Date().toISOString(),
        seen: false,
        status: 'MESSAGE',
      };
      webSocketService.send('/app/private-message', newMessage);

      // Add the new message to the allMessages state
      setAllMessages((prevMessages) => {
        const updatedMessages = {
          ...prevMessages,
          [selectedUser]: [...(prevMessages[selectedUser] || []), newMessage],
        };
        // Save the updated messages to sessionStorage after each update
        sessionStorage.setItem('messages', JSON.stringify(updatedMessages));
        setMessages(updatedMessages[selectedUser]);
        return updatedMessages;
      });

      setMessage('');
    }
  };

  const handleTyping = (e) => {
    setMessage(e.target.value);

    if (selectedUser) {
      const typingMessage = {
        senderName: 'admin',
        receiverName: selectedUser,
        status: 'TYPING',
        message: '',
        date: new Date().toISOString(),
      };

      webSocketService.send('/app/typing', typingMessage);
    }
  };

  const selectUser = (user) => {
    setSelectedUser(user);
  };

  return (
    <div className="admin-chat-container">
      <ToastContainer />
      <div className="user-list">
        {users.map((user) => (
          <div
            key={user}
            className={`user ${selectedUser === user ? 'active' : ''}`}
            onClick={() => selectUser(user)}
          >
            <span className="user-name">{user}</span>
            {typingStatus[user] && selectedUser !== user && (
              <span className="typing-indicator">Typing...</span>
            )}
          </div>
        ))}
      </div>

      <div className="chat-area">
        {selectedUser ? (
          <>
            <div className="chat-header">Chat with {selectedUser}</div>
            <div className="messages">
              {messages.map((msg, index) => (
                <div
                  key={index}
                  className={`message ${
                    msg.senderName === 'admin' ? 'sent' : 'received'
                  }`}
                >
                  <div className="message-sender">
                    {msg.senderName === 'admin' ? 'You' : msg.senderName}
                  </div>
                  <div className="message-content">{msg.message}</div>
                  <div className="message-date">
                    {new Date(msg.date).toLocaleTimeString()}
                  </div>
                  <div className="message-status">
                    {msg.senderName === 'admin' && msg.seen ? 'Seen' : ''}
                  </div>
                </div>
              ))}
              {typingStatus[selectedUser] && (
                <div className="typing-indicator">User is typing...</div>
              )}
            </div>
            <div className="chat-footer">
              <input
                type="text"
                placeholder="Type your message..."
                value={message}
                onChange={handleTyping}
              />
              <button onClick={sendMessage}>Send</button>
            </div>
          </>
        ) : (
          <div className="chat-header">Select a user to start chatting</div>
        )}
      </div>
    </div>
  );
};
