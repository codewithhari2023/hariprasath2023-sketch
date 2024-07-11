import { Category } from "./category";

export interface Book {
    id:number
    categoryId:number;
    title: string;
    description: string;
    author: string;
    price: number;
  
}
