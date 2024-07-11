import { Component, OnInit } from '@angular/core';
import { Excel } from '../model/excel';
import { ExcelService } from '../service/excel.service';
import { BookService } from '../service/book.service';
import { Book } from '../model/book';
import { AppResponse } from '../model/app-response';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-data-admin',
  templateUrl: './data-admin.component.html',
  styleUrls: ['./data-admin.component.css']
})
export class DataAdminComponent {

  error='';
  bookForm: FormGroup;
  selectedFile: File | null = null;


  constructor(private excelservice:ExcelService,private bookservice:BookService,private formBuilder:FormBuilder,private Http:HttpClient){
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
  Excel:Excel[]=[]
  
  

  makeuserExcel(Excel:any)
  {
    this.excelservice.postUSerExcel(Excel).subscribe({ next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.Excel=response.data
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
  
 
  
}
