package com.bookshopping.bookshopping.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    private  Long id;
    private String username;
    private String password;
    private String name;
    private String role;
}
