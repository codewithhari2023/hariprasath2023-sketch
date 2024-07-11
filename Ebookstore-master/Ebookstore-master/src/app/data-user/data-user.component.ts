import { Component, OnInit } from '@angular/core';
import { BookService } from '../service/book.service';
import { AppResponse } from '../model/app-response';
import { NgForm } from '@angular/forms';
import { Book } from '../model/book';
import { ExcelService } from '../service/excel.service';
import { Excel } from '../model/excel';
import { EmailService } from '../service/email.service';
import { Email } from '../model/email';
import { OrderService } from '../service/order.service';
import { Order } from '../model/order';

@Component({
  selector: 'app-data-user',
  templateUrl: './data-user.component.html',
  styleUrls: ['./data-user.component.css']
})
export class DataUserComponent implements OnInit{
orderCartItems: any;
deliveryInfo: any;

  file="";
  error="";
  mail:Email[]=[]
 
  Orders:Order[]=[]
  constructor(private bookservice:BookService,private excel:ExcelService,private emailservice:EmailService,private orderservice:OrderService){}
  ngOnInit(): void {
    this.orderservice.getAllOrder().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.Orders=response.data
         console.log(response.data);
        }else{
          console.log("response is null");
          
        }
      },
    }); 
  }
  raiseRequest:String='';
  
  Books:Book[]=[]

  AddBook(form: NgForm) {
    // if(this.editstate==0){
    // const book: Book = {
    //   id:this.id,
    //   categoryId:this.categoryId,
    //   title: this.title,
    //   description: this.description,
    //   author: this.author,
    //   price: this.price,
    //   photo:this.photo
    
    // };
    const formData = new FormData();
    formData.append('photo', this.file);
    
    formData.append('categoryId', form.value.categoryId);
    formData.append('title', form.value.title);
    formData.append('author', form.value.author);
    formData.append('description', form.value.description);
    formData.append('price', form.value.price);

  console.log(formData);
  
  
    this.bookservice.AddBook(formData).subscribe({
      next: (response: AppResponse) => {
        this.Books=response.data;
       
      
        
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });

}
// AddBookExcel(Excel:Excel){
//  let xl:Excel={filename:'',book:[]}
//   this.excel.PostBookExcel(Excel).subscribe({ next: (response: AppResponse) => {
//     if(response&& response.data)
//     {
//     this.excel=response.data
//      console.log(response.data);
//     }else{
//       console.log("response is null");
      
//     }
//   },
// });

// }
// AddUSerExcel(Excel:Excel){
//   // let xl:Excel={filename:'',book:[]}
//    this.excel.PostBookExcel(Excel).subscribe({ next: (response: AppResponse) => {
//      if(response&& response.data)
//      {
//      this.excel=response.data
//       console.log(response.data);
//      }else{
//        console.log("response is null");
       
//      }
//    },
//  });

// }
// mailtrigger(mail:Email){
//   this.emailservice.mailtrigger(mail).subscribe({ next: (response: AppResponse) => {
//     if(response&& response.data)
//     {
//     this.mail=response.data
//      console.log(response.data);
//     }else{
//       console.log("response is null");
      
//     }
//   },
    
//    }) 

// }

// leaverequest(message:string)

// {
//   this.raiseRequest=message;
//   if(message!==null)
//   {

//   }

// }
}