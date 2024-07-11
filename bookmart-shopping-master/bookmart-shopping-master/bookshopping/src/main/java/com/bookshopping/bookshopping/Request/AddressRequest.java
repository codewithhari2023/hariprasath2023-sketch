package com.bookshopping.bookshopping.Request;

import lombok.*;

import javax.validation.constraints.Size;
import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressRequest {
    private Long id;
    private Long userId;
    @Size(min = 5, message = "address should have at least 6 characters")
    private String address;
    @Size(min = 3, message = "city should have at least 3 characters")
    private String city;
    @Size(max = 6, message = "zipcode should have atmost 6 characters")
    private Integer zipcode;

}
