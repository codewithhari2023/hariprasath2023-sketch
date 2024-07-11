package com.bookshopping.bookshopping.Controller.admin;

import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RolesAllowed(Role.ADMIN)
public class AdminUserController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private Userservice userService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllUsers() {
        List<AppUser> appusers = userService.findAll();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(appusers);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
