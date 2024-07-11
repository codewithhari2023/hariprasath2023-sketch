// websocket.service.ts
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { Injectable } from '@angular/core';
import { myRxStompConfig } from '../Config/my-rx-stomp.config';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor(private rxStompService: RxStompService) {
    rxStompService.configure(myRxStompConfig);
    rxStompService.activate();
  }

  sendMessage(message: string) {
    this.rxStompService.publish({ destination: '/app/sendMessage', body: message });
  }

  getMessages() {
    return this.rxStompService.watch('/topic/messages');
  }
}

// my-rx-stomp.config.ts
