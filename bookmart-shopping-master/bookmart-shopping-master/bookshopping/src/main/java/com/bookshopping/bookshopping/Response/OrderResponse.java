package com.bookshopping.bookshopping.Response;

import com.bookshopping.bookshopping.Model.Address;
import com.bookshopping.bookshopping.Model.OrderedBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long userId;
    private Long id;
    private List<OrderedBook> bookList;
    private Long userid;
    private String name;
    private String username;
    private Address address;
    private String orderStatus;

}
