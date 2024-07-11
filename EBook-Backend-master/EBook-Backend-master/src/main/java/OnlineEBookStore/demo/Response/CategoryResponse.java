package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Request.CategoryRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    private List<CategoryRequest> categories=new ArrayList<>();
}
