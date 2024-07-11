package com.bookshopping.bookshopping.Response;

import com.bookshopping.bookshopping.Model.Address;
import com.bookshopping.bookshopping.Request.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor

public class AddressResponse {
    private List<Address> addressList;
}
