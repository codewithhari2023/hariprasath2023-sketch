package com.bookshopping.bookshopping.Controller.admin;

import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.CategoryRequest;
import com.bookshopping.bookshopping.Response.CategoryResponse;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.Categoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/admin/category")

@RolesAllowed(Role.ADMIN)
public class AdminCategoryController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private Categoryservice categoryService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCategories() {
        CategoryResponse categoryResponse = categoryService.findall();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponse.getCategories());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createCategory(@RequestBody
                                                      CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponse.getCategories());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse> updateCategory(@RequestBody
                                                      CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.update(categoryRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponse.getCategories());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.deleteByid(id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponse.getCategories());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
