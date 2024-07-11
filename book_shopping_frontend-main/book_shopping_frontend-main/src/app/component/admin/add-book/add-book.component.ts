import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppResponse } from 'src/app/model/appResponse';
import { Book } from 'src/app/model/book';
import { Category } from 'src/app/model/category';
import { BookService } from 'src/app/service/book.service';
import { CategoriesService } from 'src/app/service/categories.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
})
export class AddBookComponent implements OnInit {
  error: string = '';
  categoryId:number=0;
  Books: Book[] = [];
  title: string = '';
  description: string = '';
  author: string = '';
  price: number = 0;
  id:number=0;
  editstate:number=0;
  buttontxt: string="";
  photo:string='';
  Categories:Category[]=[]

  value:Book={
    id:0,
    title:'',
  description: '',
  author: '',
  price:0,
  categoryId:0,
  photo:""
  };
  file="";
  constructor(private bookservice: BookService,private categoryservice:CategoriesService) {}

  ngOnInit(): void {
    this.bookservice.getBook().subscribe({
      next: (Response: any) => {
        this.Books = Response.data;
        console.log(this.Books);
        
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }
  getcategories(){ 
     this.categoryservice.getCategories().subscribe({
      next:(response:any)=>{
        this.Categories=response.data
      }

     });
  }


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

  
  
    this.bookservice.postBook(formData).subscribe({
      next: (response: AppResponse) => {
        this.Books=response.data;
        this.ngOnInit();
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  // }else{
  //   const book: Book = {
  //     id:this.editstate,
  //     categoryId:this.categoryId,
  //     title: this.title,
  //     description: this.description,
  //     author: this.author,
  //     price: this.price,
  //     photo:this.photo
  //   };
  //   this.bookservice.UpdateBook(book).subscribe({
  //     next: (response: AppResponse) => {
  //       this.Books = response.data;
  //       this.editstate=0;
  //       this.ngOnInit();
  //     },
  //     error: (err) => {
  //       let message: string = err.error?.error?.message;
  //       this.error = message.includes(',') ? message.split(',')[0] : message;
  //     },
  //   });
  // }
  // this.buttontxt='Add';
 
 }
   updateBook(book:Book) : void{
    this.categoryId=book.categoryId,
    this.author=book.author,
    this.price=book.price,
    this.description=book.description,
    this.title=book.title;
    this.editstate=book.id;
    this.buttontxt='Edit';
  }
  
  DeleteBook(id: number) {
      this.bookservice.deleteBook(id).subscribe({
        next: (response ) => {
          this.Books = this.Books.filter(
            (book) => book.id !== id)
          this.ngOnInit();
        },
        error: (err) => {
          let message: string = err.error?.error?.message;
          this.error = message.includes(',') ? message.split(',')[0] : message;
        },
      });
    
  }
  onFileChange(event: any) {
    const fileInput = event.target;
    if (fileInput && fileInput.files.length > 0) {
      this.file = fileInput.files[0];

      // console.log('Selected file',this.file);
    }
  }

}