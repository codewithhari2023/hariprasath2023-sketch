import { Book } from "./book";

export interface Cart {
    id:number,
    userId:number,
    bookId:number,
    book:Book,
    count:number
}
