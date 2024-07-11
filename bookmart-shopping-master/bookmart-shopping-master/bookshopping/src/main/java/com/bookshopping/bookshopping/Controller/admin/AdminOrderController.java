package com.bookshopping.bookshopping.Controller.admin;

import com.bookshopping.bookshopping.Model.OrderStatus;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.OrderStatusRequest;
import com.bookshopping.bookshopping.Response.OrderResponse;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/admin/order")
@RolesAllowed(Role.ADMIN)
public class AdminOrderController {
@Autowired
    private APIResponse apiResponse;
@Autowired
    private OrderService orderService;
@GetMapping("/all")
public ResponseEntity<APIResponse>getallorders(){
    List<OrderResponse>orderResponses=orderService.getAllOrders();
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(orderResponses);
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
@GetMapping("/status/all")
public ResponseEntity<APIResponse>getallorderstatus(){
    List<OrderStatus>orderResponses=orderService.getAllOrderStatus();
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(orderResponses);
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
@PutMapping("/status")
    public ResponseEntity<APIResponse>updateorderstatus(@RequestBody OrderStatusRequest orderStatusRequest){
        List<OrderResponse>orderlist=orderService.updateOrderStatus(orderStatusRequest.getOrderId(),orderStatusRequest.getStatusId());
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderlist);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse>getuserorders(@PathVariable Long Id){
    List<OrderResponse>orderList=orderService.getUserOrders(Id);
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(orderList);
    return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
