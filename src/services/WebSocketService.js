import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class WebSocketService {
    constructor() {
        this.client = null;
        this.subscribers = [];
    }

    connect() {
        const protocol = window.location.protocol === 'https:' ? 'https:' : 'http:';
        const host = window.location.hostname === 'localhost' ? 'localhost:8080' : window.location.host;
        const socket = new SockJS(`${protocol}//${host}/ws`);
        this.client = new Client({
            webSocketFactory: () => socket,
            debug: (str) => {
                // console.log(str);
            },
            onConnect: () => {
                console.log('STOMP Connected');
                this.client.subscribe('/topic/locations', (message) => {
                    const payload = JSON.parse(message.body);
                    this.subscribers.forEach((callback) => callback(payload));
                });
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            },
            onWebSocketClose: () => {
                console.log('WebSocket connection closed');
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        this.client.activate();
    }

    subscribe(callback) {
        this.subscribers.push(callback);
        return () => {
            this.subscribers = this.subscribers.filter((s) => s !== callback);
        };
    }

    disconnect() {
        if (this.client) {
            this.client.deactivate();
        }
    }
}

export default new WebSocketService();
