package com.bookshopping.bookshopping.Response;

import com.bookshopping.bookshopping.Model.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookResponse {
    private List<Book> books;
}
