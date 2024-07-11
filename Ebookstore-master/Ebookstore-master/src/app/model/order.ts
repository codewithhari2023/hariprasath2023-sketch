import { Address } from "./address"
import { Book } from "./book"
import { Card } from "./card"
import { Cart } from "./cart"
import { Status } from "./status"

export interface Order {
    userId:number,
    addressId:number, 
    deliveryaddress?:Address
  username?:string
  orderStatus?:string,
  id:number,
  statusId:number,
  orderId?:number
  bookList?:Book[]
  OrderedAt?:string
  Cart?:Cart[]
  Card?:Card
}
