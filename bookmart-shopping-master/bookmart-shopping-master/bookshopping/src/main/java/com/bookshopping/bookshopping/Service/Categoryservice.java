package com.bookshopping.bookshopping.Service;

import com.bookshopping.bookshopping.Dto.CategoryDto;
import com.bookshopping.bookshopping.Model.Category;
import com.bookshopping.bookshopping.Repository.CategoryRepository;
import com.bookshopping.bookshopping.Request.CategoryRequest;
import com.bookshopping.bookshopping.Response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Categoryservice {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryDto categoryDto;
    public CategoryResponse findall(){
        List<Category>categories=categoryRepository.findAll();
        return categoryDto.mapToCategoryResponse(categories);
    }
    public CategoryResponse create(CategoryRequest categoryRequest){
        Category category=categoryDto.mapToCategory(categoryRequest);
        categoryRepository.save(category);
        return findall();
    }
    public CategoryResponse update(CategoryRequest categoryRequest){
        Category category=categoryDto.mapToCategory(categoryRequest);
        categoryRepository.save(category);
        return findall();
    }
public  CategoryResponse deleteByid(Long id){
    categoryRepository.deleteById(id);
    return findall();
}


}
