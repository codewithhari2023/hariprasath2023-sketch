package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Request.CategoryRequest;
import OnlineEBookStore.demo.Response.CategoryResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private CategoryService categoryService;

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

}
