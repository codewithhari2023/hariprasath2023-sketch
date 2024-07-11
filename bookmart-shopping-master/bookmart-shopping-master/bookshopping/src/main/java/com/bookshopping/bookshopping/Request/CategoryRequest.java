package com.bookshopping.bookshopping.Request;

import com.bookshopping.bookshopping.Model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private Long id;
    @Size(min = 3, message = "category should have at least 3 characters")
    private String title;

}
