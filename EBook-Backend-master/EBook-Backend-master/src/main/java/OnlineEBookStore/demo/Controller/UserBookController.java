package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("api/user/book")
public class UserBookController {
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
