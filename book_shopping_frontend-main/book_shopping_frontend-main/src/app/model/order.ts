import { Address } from "./address"
import { Book } from "./book"

export interface Order {
    userId?:number,
  addressId?:number, 
  address?:Address
  username?:string
  status?:string,
  orderStatus?:string
  id:number,
  statusID:number,
  book?:Book
  OrderedAt?:string
}
