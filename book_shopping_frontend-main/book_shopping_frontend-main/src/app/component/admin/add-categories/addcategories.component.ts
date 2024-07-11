import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/appResponse';
import { Category } from 'src/app/model/category';
import { CategoriesService } from 'src/app/service/categories.service';

@Component({
  selector: 'app-add-categories',
  templateUrl: './addcategories.component.html',
  styleUrls: ['./add-categories.component.css'],
})
export class AddCategoriesComponent implements OnInit {
  error: string = '';
editstate:number=0;
  categories: Category[] = [];
  title: string = '';
  id:number=0;
  buttontxt: string="";

  constructor(private categoryservice: CategoriesService) {}
  ngOnInit(): void {
    this.categoryservice.getCategories().subscribe({
      next: (Response: any) => {
        this.categories = Response.data;
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }

  addcategory(form: any) {
    if(this.editstate==0){
    const category: Category = {
      id:this.id,
      title: this.title,
    };
    this.categoryservice.postCategory(category).subscribe({
      next: (response: AppResponse) => {
        this.categories.push(response.data);
        this.ngOnInit();
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }else {
    const category: Category = {
      id:this.editstate,
      title: this.title,
    };
    this.categoryservice.Updatecategory(category).subscribe({
      next: (response: AppResponse) => {
        this.categories=response.data;
       this.editstate=0;
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }
  this.buttontxt='Add';
  this.title='';
 
 }
   updateCategory(id:number) : void{
    this.title=this.categories.find((category)=>category.id === id)?.title!;
    this.buttontxt='Edit';
    this.editstate=id;
  }

  DeleteCATEGORY(id: number ) {
    console.log("hariiii");
    
      this.categoryservice.deleteCategory(id).subscribe({
        next: (response: any) => {
          this.categories = this.categories.filter(
            (category) => category.id !== id
          );
        },
        error: (err) => {
          let message: string = err?.error?.error?.message;
          this.error =
            message != null && message.includes(',')
              ? message.split(',')[0]
              : message;
        },
      });
    
  }
}

