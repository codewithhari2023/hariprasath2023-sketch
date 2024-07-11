package com.bookshopping.bookshopping.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private Long id;
    private Long categoryId;
    @Size(min = 4, message = "title should have at least 4 characters")
    private String title;
    private String description;
    @Size(min = 4, message = "author should have at least 4 characters")
    private String author;
    @Size(min = 2, message = "price should have at least 4 characters")
    private Double price;
    private String photo;
}
