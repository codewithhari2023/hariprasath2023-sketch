package com.bookshopping.bookshopping.Controller;

import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.LoginRequest;
import com.bookshopping.bookshopping.Request.RegisterRequest;
import com.bookshopping.bookshopping.Response.AuthResponse;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/auth")
@RolesAllowed(Role.USER)

public class AuthController {
@Autowired
    private Userservice userservice;
@Autowired
    private APIResponse apiResponse;
@PostMapping("/login")
public ResponseEntity<APIResponse>login(@RequestBody LoginRequest loginRequest){
  AuthResponse loginuser=userservice.login(loginRequest);
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(loginuser);
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
    @PostMapping("/register")
    public  ResponseEntity<APIResponse>register(@RequestBody RegisterRequest registerRequest){
    AuthResponse registeruser=userservice.register(registerRequest);
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(registeruser);
    return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
