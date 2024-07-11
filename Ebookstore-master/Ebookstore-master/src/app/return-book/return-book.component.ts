import { Component, OnInit } from '@angular/core';
import { ReturnBook } from '../model/return-book';
import { ReturnbooksService } from '../service/returnbooks.service';

@Component({
  selector: 'app-return-book',
  templateUrl: './return-book.component.html',
  styleUrls: ['./return-book.component.css']
})
export class ReturnBookComponent implements OnInit {
  returnBooks: ReturnBook[] = [];
  constructor(private returnservice: ReturnbooksService) {}
  ngOnInit(): void {
    this.returnservice.returnedBook().subscribe({
      next: (Response) => {
        console.log(Response);
        this.returnBooks = Response.data;
        console.log(this.returnBooks);
        
      },
    });
  }
}
