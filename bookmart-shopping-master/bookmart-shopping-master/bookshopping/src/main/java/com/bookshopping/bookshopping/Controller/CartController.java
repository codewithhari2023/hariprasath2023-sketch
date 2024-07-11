package com.bookshopping.bookshopping.Controller;


import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.CartRequest;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/cart")
@RolesAllowed(Role.USER)
public class CartController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUsersCart(@PathVariable Long userId) {
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartService.findUserCart(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> addToCart(@RequestBody CartRequest cartRequest) {
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartService.addToCart(cartRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{bookCart}")
    public ResponseEntity<APIResponse> deleteBookFromCart(@PathVariable Long userId,
                                                          @PathVariable Long bookCart) {
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartService.deleteBookFromCart(userId, bookCart));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}