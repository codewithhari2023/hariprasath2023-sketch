package OnlineEBookStore.demo.Controller.Admin;

import OnlineEBookStore.demo.Request.BorrowRequest;
import OnlineEBookStore.demo.Response.BorrowResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/history")
public class AdminHistoryController {
    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private BorrowService userHistoryService;


    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCategories() {
        List<BorrowResponse> userHistoryResponses=userHistoryService.findAll();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(userHistoryResponses);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/issue-book")
    public ResponseEntity<APIResponse> issueBook(@RequestBody BorrowRequest userHistoryRequest) {
        List<BorrowResponse> userHistoryList = userHistoryService.issueBook(userHistoryRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(userHistoryList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/return-book")
    public ResponseEntity<APIResponse> returnBook(@RequestBody BorrowRequest userHistoryRequest) {
        List<BorrowResponse> userHistoryList = userHistoryService.returnBook(userHistoryRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(userHistoryList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
