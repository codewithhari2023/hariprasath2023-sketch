package com.bookshopping.bookshopping.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Long userId;
    private Long bookId;
    private Integer count;
}
