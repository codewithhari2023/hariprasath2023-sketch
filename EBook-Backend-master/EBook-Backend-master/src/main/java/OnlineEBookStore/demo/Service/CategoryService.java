package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.CategoryDto;
import OnlineEBookStore.demo.Model.Category;
import OnlineEBookStore.demo.Repository.CategoryRepository;
import OnlineEBookStore.demo.Request.CategoryRequest;
import OnlineEBookStore.demo.Response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryDto categoryDto;
    public CategoryResponse findall(){
        List<Category> categories=categoryRepository.findAll();
        return categoryDto.mapToCategoryResponse(categories);
    }

    public CategoryResponse create(CategoryRequest categoryRequest){
        Category category=categoryDto.mapToCategory(categoryRequest);
        categoryRepository.save(category);
        return findall();
    }

    public CategoryResponse update(CategoryRequest categoryRequest) {
        Category category=categoryDto.mapToCategory(categoryRequest);
        categoryRepository.save(category);
        return findall();
    }

    public CategoryResponse deleteByid(Long id) {
        categoryRepository.deleteById(id);
        return findall();
    }
}
