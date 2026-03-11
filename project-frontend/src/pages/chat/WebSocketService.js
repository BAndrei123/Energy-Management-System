import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SOCKET_URL = 'http://chat-spring.localhost/ws'; // Your Spring WebSocket URL

class WebSocketService {
  constructor() {
    this.client = null;
  }

  connect(onMessageReceived, onConnect) {
    this.client = new Client({
      webSocketFactory: () => new SockJS(SOCKET_URL),
      onConnect: () => {
        console.log('WebSocket connected');
        onConnect?.();
      },
      onDisconnect: () => {
        console.log('WebSocket disconnected');
      },
      onStompError: (error) => {
        console.error('STOMP error', error);
      },
    });

    this.client.onUnhandledMessage = (message) => {
      onMessageReceived(JSON.parse(message.body));
    };

    this.client.activate();
  }

  subscribe(destination, callback) {
    if (this.client && this.client.connected) {
      return this.client.subscribe(destination, (message) =>
        callback(JSON.parse(message.body))
      );
    }
  }

  send(destination, body) {
    if (this.client && this.client.connected) {
      this.client.publish({ destination, body: JSON.stringify(body) });
    }
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
    }
  }
}

export const webSocketService = new WebSocketService();
