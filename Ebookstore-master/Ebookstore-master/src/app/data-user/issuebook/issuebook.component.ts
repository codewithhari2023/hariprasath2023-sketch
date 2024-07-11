import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppResponse } from 'src/app/model/app-response';
import { Borrow } from 'src/app/model/borrow';
import { IssueBook } from 'src/app/model/issue-book';
import { ReturnBook } from 'src/app/model/return-book';
import { Userhistory } from 'src/app/model/user-history';
import { BorrowService } from 'src/app/service/borrow.service';
import { IssuedBookService } from 'src/app/service/issued-book.service';
import { ReturnbooksService } from 'src/app/service/returnbooks.service';
import { UserhistoryService } from 'src/app/service/userhistory.service';
import { UserHistoryComponent } from 'src/app/user-history/user-history.component';


@Component({
  selector: 'app-issuebook',
  templateUrl: './issuebook.component.html',
  styleUrls: ['./issuebook.component.css']
})
export class IssuebookComponent {

  constructor(private issuebookservice:IssuedBookService,private userhistoryservice:UserhistoryService,private returnservice: ReturnbooksService,private borrowservice:BorrowService,private datePipe:DatePipe ){}
    BookId:number=0
    StudentId:number=0
    issuebooks:Userhistory[]=[]
    returnBooks: ReturnBook[] = [];
    borrow: Borrow = { issueDate: '', ReturnDate: '' };
    penaltyAmount: number | undefined;
    Sno:number=1
     currentDate = new Date();
      
     
     
     year = this.currentDate.getFullYear();
     month = String(this.currentDate.getMonth() + 1).padStart(2, '0');
     day = String(this.currentDate.getDate()).padStart(2, '0');
    
     issuedDate = `${this.day}-${this.month}-${this.year}`;
     ReturnDate= `${(this.currentDate.getDate()+2)}-${this.month}-${this.year}`; 

  ngOnInit(): void {
    console.log(this.currentDate);

    
    this.issuebookservice.getIssuebook().subscribe({
      next: (response: AppResponse) => {
      
        if (response && response.data) {
          this.issuebooks = response.data;
          console.log(response.data);
          
        } else {
          console.error('Invalid API response format:', response);
      
        }
      },
      error: (err) => {
        console.log('An error occurred:', err);
      
      },
      complete: () => console.log('There are no more actions happening.'),
    });
    this.userhistoryservice.getUsersHistory().subscribe({
      next: (response: AppResponse) => {
      console.log("jiiiii")
        if (response && response.data) {
          this.issuebooks = response.data;
          console.log(this.issuebooks)
        } else {
          console.error('Invalid API response format:', response);
       
        }
      },
      error: (err) => {
        console.log('An error occurred:', err);
      
      },
      complete: () => console.log('There are no more actions happening.'),
    });
    this.returnservice.returnedBook().subscribe({
      next: (Response) => {
        console.log(Response);
        this.returnBooks = Response.data;
        console.log(this.returnBooks);
        
      },
    });
  }

onSubmit(issueBookForm: any) {
  let issueBook =issueBookForm.value;
  console.log(issueBook)
  this.issuebookservice.issueBook(issueBook).subscribe({
    next:(resp)=>{
      this.ngOnInit()
    }
  
  }) 

}
calculatePenalty() {
  this.borrowservice.calculatePenalty(this.borrow).subscribe(
    (response: AppResponse) => {
      this.penaltyAmount = response.data;
      alert('Penalty Amount: $' + this.penaltyAmount);
    },
    error => {
      console.error('Error:', error);
      alert('Error calculating penalty. Please try again.');
    }
  );
}
}
