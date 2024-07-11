package com.bookshopping.bookshopping.Dto;

import com.bookshopping.bookshopping.Model.Book;
import com.bookshopping.bookshopping.Request.BookRequest;
import com.bookshopping.bookshopping.Response.BookResponse;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BookDto {
    public BookResponse mapToBookResponse(List<Book> bookList) {
        return new BookResponse();
    }

    public Book mapToBook(BookRequest bookRequest) {
        Book book = new Book();
        if (bookRequest.getId() != null) {
            book.setId(bookRequest.getId());
        }
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setDescription(bookRequest.getDescription());
        book.setTitle(bookRequest.getTitle());
        book.setPhoto(bookRequest.getPhoto());
        return book;
    }
}
