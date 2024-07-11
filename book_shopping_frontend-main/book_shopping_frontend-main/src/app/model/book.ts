import { Category } from "./category";

export interface Book {
  id: number;
  category?:Category;
  title: string;
  description: string;
  author: string;
  price: number;
  categoryId:number;
  photo:string;
   
}
