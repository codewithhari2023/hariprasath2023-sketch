package com.bookshopping.bookshopping.Response;

import com.bookshopping.bookshopping.Model.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    private AppUser user;
}
