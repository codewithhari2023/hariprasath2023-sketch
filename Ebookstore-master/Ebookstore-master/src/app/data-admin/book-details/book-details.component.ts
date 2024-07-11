import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { AppResponse } from 'src/app/model/app-response';
import { Barcode } from 'src/app/model/barcode';
import { Book } from 'src/app/model/book';
import { BarcodeService } from 'src/app/service/barcode.service';
import { BookService } from 'src/app/service/book.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  error='';
  bookForm: FormGroup;
  selectedFile: File | null = null;
title: any;
Barcode:Barcode[]=[]
  barcodeImageUrl!: string | ArrayBuffer;
http: any;
  constructor(private bookservice:BookService,private formBuilder:FormBuilder,private barcode:BarcodeService){
    this.bookForm = this.formBuilder.group({
      categoryId: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
      author: ['', Validators.required],
      price: ['', Validators.required],
      photo: [''] // No initial value for photo
    });
  }
  
  book:Book[]=[]
  message='';
  
  
  

  ngOnInit(): void {
    this.bookservice.GetBooks().subscribe({ next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.book=response.data
       console.log(response.data);
      }else{
        console.log("response is null");
        
      }
    },
      
     })
  }

  onSubmit(): void {
    if (this.bookForm.valid) {
      const formData = new FormData();
      formData.append('categoryId', this.bookForm.get('categoryId')!.value.toString());
      formData.append('title', this.bookForm.get('title')!.value);
      formData.append('description', this.bookForm.get('description')!.value);
      formData.append('author', this.bookForm.get('author')!.value);
      formData.append('price', this.bookForm.get('price')!.value.toString());
      if (this.selectedFile) {
        formData.append('photo', this.selectedFile, this.selectedFile.name);
      }

      this.bookservice.AddBook(formData).subscribe({ next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.book=response.data
         console.log(response.data);
         this.bookForm.reset();
         this.selectedFile = null;
        }else{
          console.log("response is null");
          
        }
      },
        
       })
     // Handle the API response as needed
       // Reset the form after successful submission
         // Reset the selected file
    
    }
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] as File;
  } 
  generateBarcodes() {
    const bookBarcodeRequest = {
      books: this.book
    };

    this.barcode
      .postBarcode(bookBarcodeRequest)
      .subscribe((response) => {
        this.downloadBarcodeImage(response);
      });
  }
  downloadBarcodeImage(response:any) {
    const url = window.URL.createObjectURL(response);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'barcode.png';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }
      



}