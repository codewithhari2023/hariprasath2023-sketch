// chat.service.ts
import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
    
//     private client: Client;
//     private messages: Subject<ChatMessage> = new Subject<ChatMessage>();
//   username!: string;
  
//     constructor() {
//       this.client = new Client({
//         brokerURL: 'ws://localhost:8080/ws',
//         connectHeaders: {},
//         debug: str => console.log(str),
//         reconnectDelay: 5000,
//         heartbeatIncoming: 4000,
//         heartbeatOutgoing: 4000,
//         webSocketFactory: () => new SockJS('/ws')
//       });
  
//       this.client.onConnect = () => {
//         this.client.subscribe('/topic/public', message => {
//           this.messages.next(JSON.parse(message.body));
//         });
//       };
  
//       this.client.onStompError = frame => {
//         console.error('Broker reported error: ' + frame.headers['message']);
//         console.error('Additional details: ' + frame.body);
//       };
  
//       this.client.activate();
//     }
  
//     sendMessage(message: ChatMessage) {
//       this.client.publish({ destination: '/app/chat.sendMessage', body: JSON.stringify(message) });
//     }
  
//     addUser(user: ChatMessage) {
//       this.client.publish({ destination: '/app/chat.addUser', body: JSON.stringify(user) });
//     }
  
//     getMessages() {
//       return this.messages.asObservable();
//     }
//   }
  
//   export interface ChatMessage {
//     sender: string;
//     content: string;
//     type: 'CHAT' | 'JOIN' | 'LEAVE';
//   }
}