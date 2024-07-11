import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { AppResponse } from '../model/app-response';
import { Userhistory } from '../model/user-history';
import { StorageService } from '../service/storage.service';
import { UserhistoryService } from '../service/userhistory.service';
import { ReturnbooksService } from '../service/returnbooks.service';
import { OrderService } from '../service/order.service';
import { Order } from '../model/order';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {
  fineAmount: number | null = null;
  id: number = 0;
  Sno:number=1;
orders:Order[]=[];

  constructor(
    private userhistoryservice: UserhistoryService,
    private storageservice: StorageService,
    private returnservice: ReturnbooksService,
    private order:OrderService,
    private datePipe: DatePipe
  ) {}

  userbooks: Userhistory[] = [];


  ngOnInit(): void {
    this.loadUserHistory();
    console.log(this.userbooks.length);
    this.order.getUserOrder().subscribe({
      next: (response: AppResponse) => {
        this.orders = response.data;
        console.log(this.orders);
      },
      error: (err) => {
        console.log('An error occurred:', err);
      },
      complete: () => console.log('There are no more actions happening.'),
    });
  }

  loadUserHistory(): void {
    let user = this.storageservice.getLoggedInUser();
    console.log(user);

    this.userhistoryservice.getUserHistory().subscribe({
      next: (response: AppResponse) => {
        this.userbooks = response.data;
        console.log(response.data);
      },
      error: (err) => {
        console.log('An error occurred:', err);
      },
      complete: () => console.log('There are no more actions happening.'),
    });
  }

  returnBook(ids: number): void {
    console.log(ids);
      var amt=this.fineAmount;

 
      this.returnservice.returnBook(ids,amt).subscribe({
        next: (response) => {},
      });
    

   
  }

  // onSubmit(categoryForm: NgForm): void {
  //   let date1 = new Date(categoryForm.value.FirstInput);
  //   console.log(date1);
  //   let date2 = new Date(categoryForm.value.FirstInput);
  //   console.log(date2);
  //   const differenceInDays = this.calculateDifferenceInDays(date1, date2);
  //   const finePerDay = 2; // You can adjust this value
  //   this.fineAmount = differenceInDays > 0 ? differenceInDays * finePerDay : 0;
  // }

  // getCurrentDate(): Date {
  //   return new Date();
  // }


  private calculateDifferenceInDays(returndate: Date, currentdate: Date): number {
    const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
    const diffDays = Math.round(Math.abs((returndate.getTime() - currentdate.getTime()) / oneDay));
    return diffDays;
  }

  popup(userbook: Userhistory): void {
    this.id = userbook.id;
    let returndate = userbook.returnDate;
    let currentDate: Date = new Date();
    console.log(returndate);
    console.log(currentDate);

    const returnDate: Date = new Date(returndate);

    const differenceInDays = this.calculateDifferenceInDays(returnDate, currentDate);
    const finePerDay = 2; // You can adjust this value
    this.fineAmount = differenceInDays > 0 ? differenceInDays * finePerDay : 0;
  }
  
}
