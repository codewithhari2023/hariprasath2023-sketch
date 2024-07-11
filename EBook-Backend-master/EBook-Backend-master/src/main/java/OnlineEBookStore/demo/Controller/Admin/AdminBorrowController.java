package OnlineEBookStore.demo.Controller.Admin;

import OnlineEBookStore.demo.Model.Borrow;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminBorrowController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private APIResponse apiResponse;

    @GetMapping("/returnedBook")
    public ResponseEntity<APIResponse> getReturnedBook() {
        List<Borrow> returnBook = borrowService.returnedBook();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(returnBook);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
