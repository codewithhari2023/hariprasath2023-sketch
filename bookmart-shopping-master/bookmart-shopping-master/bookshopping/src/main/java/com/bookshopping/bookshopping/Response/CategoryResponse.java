package com.bookshopping.bookshopping.Response;

import com.bookshopping.bookshopping.Model.Category;
import com.bookshopping.bookshopping.Request.CategoryRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CategoryResponse {
    private List<CategoryRequest> categories=new ArrayList<>();
}
