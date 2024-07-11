import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ChatMessageDto } from '../model/chatmessage-dto';
import { WebsocketService } from '../service/websocket.service';
import { Router } from '@angular/router';
import { CommonUser } from '../model/common-user';
import { Subscription } from 'rxjs';
import { UserService } from '../service/user.service';
import { AppResponse } from '../model/app-response';
import { ConversationResponse } from '../model/ConversationResponse';
import { MessageRequest } from '../model/MessageRequest';
import { MessageResponse } from '../model/MessageResponse';
import { WebSocketResponse } from '../model/WebSocketResponse';
import { StorageService } from '../service/storage.service';
import { StompService } from '../service/stomp.service';




@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  
  // all users except current user
  users: CommonUser[] = [];
  // users all conversations
  userConversations: ConversationResponse[] = [];
  // current user conversation subscription
  stompUserSub: Subscription | undefined;

  // selected conversation
  selectedConversationId: number = -1;
  selectedConversationReceiverId: number = -1;
  selectedConversationReceiverName: String = '';
  // selected conversation messages
  selectedConversation: MessageResponse[] = [];
  // selected conversation messages subscription
  stompConvSub: Subscription | undefined;

  // Boolean flag to indicate whether showing users or conversation on left column
  showUserState: boolean = false;
  // Input field for send message
  message: string = '';
  error!: string;
  currentUser: number;

  constructor(
    private router: Router,
    private userService: UserService,
    private stomp:StompService,
    private storage:StorageService
  ) {
    this.currentUser = storage.getLoggedInUser().id;
  }

  ngOnInit(): void {
    // Subscribe to userId websocket to get updated conversation when gets new messages
    this.userService.getUser().subscribe({
      next: (response: any) => {
        this.users=response.data
        console.log(this.users);
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });


    this.subscribeToCurrentUserConversation();
  }

  ngOnDestroy(): void {
    // Unsubscribe from all channels onDestroy
    this.stompUserSub?.unsubscribe();
    this.stompConvSub?.unsubscribe();
  }

  // When click the new/add button Then get all users and set users list
  onShowHideUserConversation() {
    this.showUserState = !this.showUserState;
    if (this.showUserState) {
      this.userService
        .getAllUsersExceptCurrentUser()
        .subscribe((res: AppResponse) => {
          this.users = res.data;
          console.log(this.users);
          
        });
    }
  }

  // Close a chat from dropdown menu
  onCloseChat() {
    this.stompConvSub?.unsubscribe();
    this.selectedConversationId = -1;
  }

  // When click logout button Then remove user from localStorage and navigate to homepage
  onUserLogout() {
    localStorage.removeItem('user');
    this.router.navigate(['.']);
  }

  subscribeToCurrentUserConversation() {
    // setting one second delayed to successfully connect the stomp to server
    setTimeout(() => {
      this.stompUserSub = this.stomp.subscribe(
        `user/` + this.currentUser,
        (payload: any) => {
          let res: WebSocketResponse = payload;
          if (res.type == 'ALL') {
            this.userConversations = res.data;
            console.log("subscribeToCurrentUserConversation");
            
            const found = this.userConversations.find(
              (item) => item.conversationId === this.selectedConversationId
            );
            if (found === undefined) {
              this.onCloseChat();
            }
          }
        }
      );
      // Notify that I'm subscribed to get initial data
      this.stomp.send(`user`, this.currentUser);
    }, 1000);
  }

  // When new or exiting user selected Then set the variables and get the two users
  // conversationId from the database
  onUserSelected(receiverId: number) {
  
    this.userService
      .getConversationIdByUser1IdAndUser2Id(receiverId, this.currentUser)
      .subscribe((res: AppResponse) => {
        this.selectedConversationId = res.data;
        console.log(this.selectedConversationId);
        
        this.onShowHideUserConversation();
        this.setConversation();
      });
  }

  // When user select a conversation from the list
  onConversationSelected(index: number) {
    this.selectedConversationId = this.userConversations[index].conversationId;
    this.selectedConversationReceiverId =
      this.userConversations[index].otherUserId;
    this.selectedConversationReceiverName =
      this.userConversations[index].otherUserName;

    this.setConversation();
  }

  // Set a conversation of selected conversationId
  setConversation() {
    // unsubscribe any previous subscription
    this.stompConvSub?.unsubscribe();
    // then subscribe to selected conversation
    // when get new message then add the message to first of the array
    // this.stompConvSub = this.stomp.subscribe(
    //   'conv/' + this.selectedConversationId,
    //   (payload: any) => {
    //     let res: WebSocketResponse = payload;
    //     if (res.type == 'ALL') {
    //       this.selectedConversation = res.data;
    //     } else if (res.type == 'ADDED') {
    //       let msg: MessageResponse = res.data;
    //       this.selectedConversation.unshift(msg);
    //     }
    //   }
    // );
    // Notify that I'm subscribed to get initial data
    // this.stomp.send('conv', this.selectedConversationId);
  }

  // Send message to other user
  onSendMessage() {
    // If message field is empty then return
    if (this.message.trim().length == 0) return;

    const timestamp = new Date();
    let body: MessageRequest = {
      conversationId: this.selectedConversationId,
      senderId: this.storage.getLoggedInUser().id,
      receiverId: this.selectedConversationReceiverId,
      message: this.message.trim(),
      timestamp: timestamp,
    };
    // this.stomp.send('sendMessage', body);
    this.message = '';
  }

  // When click Delete chat from the dropdown menu Then delete the conversation
  // with it's all messages
  onDeleteConversation() {
    this.stomp.send('deleteConversation', {
      conversationId: this.selectedConversationId,
      user1Id: this.storage.getLoggedInUser().id,
      user2Id: this.selectedConversationReceiverId,
    });
  }

  // When click delete on a message menu Then delete from database Then refresh
  // conversation list
  onDeleteMessage(messageId: number) {
    this.stomp.send('deleteMessage', {
      conversationId: this.selectedConversationId,
      messageId: messageId,
    });
  }


  
}