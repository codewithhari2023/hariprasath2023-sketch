import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { FileUploader } from 'ng2-file-upload';
import { AppResponse } from 'src/app/model/app-response';
import { Book } from 'src/app/model/book';
import { Excel } from 'src/app/model/excel';
import { BookService } from 'src/app/service/book.service';
import { ExcelService } from 'src/app/service/excel.service';
import * as XLSX from 'xlsx';
@Component({
  selector: 'app-exceluploads',
  templateUrl: './exceluploads.component.html',
  styleUrls: ['./exceluploads.component.css']
})
export class ExceluploadsComponent  implements OnInit{

  file: File | undefined;
  data: any[] = [];

  constructor(private excelService: ExcelService,private bookservice:BookService) {}
  ngOnInit(): void {
    this.bookservice.GetBooks().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.data=response.data
         console.log(response.data);
        }else{
          console.log("response is null");
          
        }
      },
    });
   }

  onFileChange(event: any): void {
    const target = event.target as HTMLInputElement;
    this.file = (target.files as FileList)[0];
  }

  onSubmit(): void {
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
}

