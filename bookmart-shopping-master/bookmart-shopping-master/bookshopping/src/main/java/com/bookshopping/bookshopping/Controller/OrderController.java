package com.bookshopping.bookshopping.Controller;

import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.OrderRequest;
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
@RequestMapping("/api/order")
@RolesAllowed(Role.USER)
public class OrderController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderResponse> orderList = orderService.getUserOrders(userId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        List<OrderResponse> orderList = orderService
                .placeOrder(orderRequest.getUserId(), orderRequest.getAddressId());
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}