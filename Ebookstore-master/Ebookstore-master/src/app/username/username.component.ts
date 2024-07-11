import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-username',
  templateUrl: './username.component.html',
  styleUrls: ['./username.component.css']
})
export class UsernameComponent {
  // username!: string;

  // constructor(private chatService: ChatService, private router: Router) { }

  // connect(event: Event) {
  //   event.preventDefault();
  //   if (this.username.trim()) {
  //     const chatMessage: ChatMessage = {
  //       sender: this.username,
  //       content: '',
  //       type: 'JOIN'
  //     };
  //     this.chatService.addUser(chatMessage);
  //     this.router.navigate(['/chat']);
  //   }
  // }
}
