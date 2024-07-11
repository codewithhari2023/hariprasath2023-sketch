package com.bookshopping.bookshopping.Controller;

import com.bookshopping.bookshopping.Model.Book;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController@RequestMapping("/api/book")
@RolesAllowed(Role.USER)
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private APIResponse apiResponse;
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllBooks() {
        List<Book> bookList = bookService.findall();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
