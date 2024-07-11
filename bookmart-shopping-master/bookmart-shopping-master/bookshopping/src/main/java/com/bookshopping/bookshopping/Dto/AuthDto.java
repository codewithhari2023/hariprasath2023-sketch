package com.bookshopping.bookshopping.Dto;

import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Request.RegisterRequest;
import com.bookshopping.bookshopping.Response.AddressResponse;
import com.bookshopping.bookshopping.Response.AuthResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthDto {
    public AppUser mapToAppUser(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequest.getUsername());
        appUser.setName(registerRequest.getName());
        appUser.setPassword(registerRequest.getPassword());
        return appUser;
    }


    public AuthResponse mapToAuthResponse(AppUser appUser) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(appUser.getId());
        authResponse.setUsername(appUser.getUsername());
        authResponse.setPassword(appUser.getPassword());
        authResponse.setRole(appUser.getRoles().getName());
        return authResponse;
    }
}
