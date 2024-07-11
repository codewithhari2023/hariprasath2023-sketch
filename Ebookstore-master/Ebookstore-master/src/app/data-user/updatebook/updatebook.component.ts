import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppResponse } from 'src/app/model/app-response';
import { Barcode } from 'src/app/model/barcode';
import { Book } from 'src/app/model/book';
import { Category } from 'src/app/model/category';
import { BookService } from 'src/app/service/book.service';
import { CategoryService } from 'src/app/service/category.service';
import { ExcelService } from 'src/app/service/excel.service';
import * as XLSX from 'xlsx';
@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent {
  error='';
  bookForm: FormGroup;
  selectedFile: File | null = null;
  Barcode:Barcode[]=[]
title: any;
Category:Category[]=[]
file: File | undefined;
data: any[] = [];
  constructor(private bookservice:BookService,private formBuilder:FormBuilder,private category:CategoryService,private excelService:ExcelService){
    this.bookForm = this.formBuilder.group({
      categoryId: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
      author: ['', Validators.required],
      price: ['', Validators.required],
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
     this.category.getCategory().subscribe({ next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.Category=response.data
       console.log(response.data);
      }else{
        console.log("response is null");
        
      }
    },
      
     })
     this.bookForm = this.formBuilder.group({
      categoryId: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      photo: [null, Validators.required] // For file upload, set as FormControl without initial value
    });


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
  AddCategory(c:Category){
    this.category.postCategory(c).subscribe({ next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.Category=response.data;
       this.bookForm.reset();
      
      }else{
        console.log("response is null");
        
      } 
    }}
    )}
    onFileChange(event: any): void {
      const target = event.target as HTMLInputElement;
      this.file = (target.files as FileList)[0];
    }
  
    onSubmits(): void {
      if (this.file) {
        const reader: FileReader = new FileReader();
        reader.onload = (e: any) => {
          const data = new Uint8Array(e.target.result);
          const workbook = XLSX.read(data, { type: 'array' });
          const sheetName = workbook.SheetNames[0];
          const worksheet = workbook.Sheets[sheetName];
          const excelData = XLSX.utils.sheet_to_json(worksheet, { raw: true });
          console.log(excelData); // Handle the data as needed
        };
        reader.readAsArrayBuffer(this.file);
      }
    }
  
    exportToExcel(): void {
      this.excelService.exportToExcel(this.data, 'books', 'Sheet1');
    }
    DeleteBook(id: number) {
      this.bookservice.DeleteBook(id).subscribe({
        next: (response ) => {
          this.book = this.book.filter(
            (book) => book.id !== id)
          this.ngOnInit();
        },
        error: (err) => {
          let message: string = err.error?.error?.message;
          this.error = message.includes(',') ? message.split(',')[0] : message;
        },
      });
    
  }
      onSubmitted(bookIds:number): void {
      
        
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

      const bookId = bookIds; // Replace with the actual book ID
      this.bookservice.updateBook(bookId, formData).subscribe({
        next: (updatedBook) => {
          console.log('Book updated successfully:', updatedBook);
          this.bookForm.reset();
          this.selectedFile = null;
        },
        error: (error) => {
          console.error('Error updating book:', error);
          // Handle error as needed
        }
      });
    }
  }
  
}
