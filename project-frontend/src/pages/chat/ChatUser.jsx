import React, { useState, useEffect } from 'react';
import { webSocketService } from './WebSocketService';
import './ChatUser.css';

export const ChatUser = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [adminTyping, setAdminTyping] = useState(false);
  const username = localStorage.getItem('username'); // Mock user name for testing

  useEffect(() => {
    const storedMessages = JSON.parse(sessionStorage.getItem(`messages_${username}`)) || [];
    setMessages(storedMessages);

    webSocketService.connect(
      (newMessage) => {
        handleIncomingMessage(newMessage);
      },
      () => {
        webSocketService.subscribe(`/user/${username}/private`, (reply) => {
          handleIncomingMessage(reply);
        });
      }
    );

    return () => {
      webSocketService.disconnect();
    };
  }, [username]);

  useEffect(()=>{
    if(messages.length>0){
      const seenNotification = {
        senderName: username,
        receiverName: 'admin',
        status: 'SEEN',
        date: new Date().toISOString(),
      };
      webSocketService.send('/app/seen', seenNotification);
    
    }
  },[messages,username])

  const handleIncomingMessage = (newMessage) => {
    if (newMessage.status === 'TYPING') {
      setAdminTyping(true);
      setTimeout(() => setAdminTyping(false), 3000);
      return;
    }

    if (newMessage.status === 'SEEN') {
      
      setMessages((prevMessages) => {
        const updatedMessages = prevMessages.map((msg) => {
          if (msg.senderName === username) {
            return { ...msg, seen: true };
          }
          return msg;
        });
        saveMessagesToSession(updatedMessages);
        return updatedMessages;
      });
      return;
    }
    

    console.log(newMessage);
    setMessages((prevMessages) => {
      const updatedMessages = [...prevMessages, newMessage];
      saveMessagesToSession(updatedMessages);
      return updatedMessages;
    });
  };

  const saveMessagesToSession = (messages) => {
    sessionStorage.setItem(`messages_${username}`, JSON.stringify(messages));
  };

  const sendMessage = () => {
    if (message.trim()) {
      const newMessage = {
        senderName: username,
        receiverName: 'admin',
        message,
        date: new Date().toISOString(),
        status: 'MESSAGE',
        seen: false,
      };
      webSocketService.send('/app/private-message', newMessage);

      setMessages((prevMessages) => {
        const updatedMessages = [...prevMessages, newMessage];
        saveMessagesToSession(updatedMessages);
        return updatedMessages;
      });

      setMessage('');
    }
  };

  const handleTyping = (e) => {
    setMessage(e.target.value);

    const typingMessage = {
      senderName: username,
      receiverName: 'admin',
      status: 'TYPING',
      message: '',
      date: new Date().toISOString(),
    };

    webSocketService.send('/app/typing', typingMessage);
  };

  return (
    <div className="chat-container">
      <div className="chat-header">Customer Support</div>
      <div className="messages">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`message ${
              msg.senderName === username ? 'sent' : 'received'
            }`}
          >
            <div className="message-sender">
              {msg.senderName === username ? 'You' : msg.senderName}
            </div>
            <div className="message-content">{msg.message}</div>
            <div className="message-date">
              {new Date(msg.date).toLocaleTimeString()}
            </div>

            <div className="message-status">
            {msg.senderName === username && msg.seen ? 'Seen' : ''}
          </div>
            
          </div>
        ))}
        {adminTyping && (
          <div className="typing-indicator">Admin is typing...</div>
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
    </div>
  );
};
