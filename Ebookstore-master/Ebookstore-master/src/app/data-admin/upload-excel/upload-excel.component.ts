import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppResponse } from 'src/app/model/app-response';
import { Excel } from 'src/app/model/excel';
import { ExcelService } from 'src/app/service/excel.service';
import { UserService } from 'src/app/service/user.service';
import * as XLSX from 'xlsx';
@Component({
  selector: 'app-upload-excel',
  templateUrl: './upload-excel.component.html',
  styleUrls: ['./upload-excel.component.css']
})
export class UploadExcelComponent {
  file: File | undefined;
  data: any[] = [];

  constructor(private excelService: ExcelService,private userservice:UserService) {}
  ngOnInit(): void {
    this.userservice.getUser().subscribe({
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
