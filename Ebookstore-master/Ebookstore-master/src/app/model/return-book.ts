import { Book } from "./book";

export interface ReturnBook {
    id: number;
  commonUser: {
    id: number;
    name: string;
  };
  book:Book;
  returned: boolean;
}