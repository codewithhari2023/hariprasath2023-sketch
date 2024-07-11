import { Component, OnInit } from '@angular/core';
import { AppResponse } from '../model/app-response';
import { Address } from '../model/address';
import { StorageService } from '../service/storage.service';
import { UserService } from '../service/user.service';
import { NgForm } from '@angular/forms';
import { Card } from '../model/card';
import { CardService } from '../service/card.service';
import { AnimationOptions } from 'ngx-lottie';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  emptycarts:AnimationOptions = {
    path: '/assets/emptycart.json',
  };

  Addresses: Address[] = [];
  city: any
  zipcode: any
  street:any
  error: string = '';
  add: Address[] = [];
  DebitCard:Card[]=[]
  expdate: any;
cvv: any;
balance: any;
cardNumber: any;
  userId = this.storageservice.getLoggedInUser().id;
  username=this.storageservice.getLoggedInUser().username;
 
  constructor(
    private userservice: UserService,
    private storageservice: StorageService,
    private cardservice:CardService
  ) {}
  ngOnInit(): void {
    this.userservice.getUseraddress().subscribe({
      next: (response: AppResponse) => {
        this.Addresses=response.data.address;
        console.log(this.Addresses);
        
      },
      error: (error: Error) => {
        console.error('Get Address Error:', error);
      },
    });
    this.cardservice.GetUserCard().subscribe({
      next: (response: AppResponse) => {
        this.DebitCard=response.data;
        console.log(this.DebitCard);
        
      },
      error: (error: Error) => {
        console.error('Get Card Error:', error);
      },
    });
    

  }

resetform(myform:NgForm){
  myform.reset()
}

  addadress() {
    const address: Address = {
      id: 0,
      street: this.street,
      zipcode: this.zipcode,
      city: this.city,
      userId: this.storageservice.getLoggedInUser().id,
    };
    console.log(address);

    this.userservice.postaddress(address).subscribe({
      next: (response: AppResponse) => {
        this.Addresses = response.data;
      },
    });
  }

  Deleteaddress(id: number) {
    this.userservice.deleteadress(id).subscribe({
      next: (response: any) => {
        this.Addresses = this.Addresses.filter(
          (address) => address.id !== id
        );
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error =
          message != null && message.includes(',')
            ? message.split(',')[0]
            : message;
      },
    });
  }
  logout() {
    this.storageservice.getLoggedInUser()
    }
    makecard() {
      let userId:number=this.storageservice.getLoggedInUser().id;
      let Card:Card={
        id:0,
        cvv:this.cvv,
        cardNumber:this.cardNumber,
        balance:this.balance,
        expirydate:this.expdate,
        userId:userId
      }
      this.cardservice.AddCard(Card).subscribe({ next: (response: any) => {
        this.DebitCard=response.data
        console.log(this.DebitCard);
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },})
      
      }
      onModalHidden() {
        this.cardNumber = '';
        this.cvv = '';
        this.expdate = '';
        this.balance = '';
      }
}
