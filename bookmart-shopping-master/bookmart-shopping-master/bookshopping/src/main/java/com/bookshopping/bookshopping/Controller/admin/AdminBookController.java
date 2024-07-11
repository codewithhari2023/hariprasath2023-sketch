package com.bookshopping.bookshopping.Controller.admin;

import com.bookshopping.bookshopping.Model.Book;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.BookRequest;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.BookService;
import com.bookshopping.bookshopping.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/book")
@RolesAllowed(Role.ADMIN)
public class AdminBookController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private BookService bookService;
    @Autowired
    private StorageService storageService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllBooks() {
        List<Book> bookList = bookService.findall();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> createbook(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            @RequestParam("price") Double price
    )throws IOException{
        String file=storageService.storeFile(photo);
        BookRequest bookRequest=new BookRequest();
        bookRequest.setPhoto(file);
        bookRequest.setAuthor(author);
        bookRequest.setDescription(description);
        bookRequest.setTitle(title);
        bookRequest.setPrice(price);
        bookRequest.setCategoryId(categoryId);
        List<Book>bookList=bookService.createbook(bookRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new  ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse> updatebook(@RequestBody
                                                  BookRequest bookRequest) {
        List<Book> bookList = bookService.updatebook(bookRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteBook(@PathVariable Long id) {
        List<Book> bookList = bookService.deleteById(id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}