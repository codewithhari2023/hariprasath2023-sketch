package com.bookshopping.bookshopping.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Getter
@Setter

public class LoginRequest {
    @NotEmpty
    @Size(min = 2, message = "Username should have at least 2 characters")
    private String username;

    @NotEmpty
    @Size(min = 2, message = "Password should have at least 2 characters")
    private String password;
}
