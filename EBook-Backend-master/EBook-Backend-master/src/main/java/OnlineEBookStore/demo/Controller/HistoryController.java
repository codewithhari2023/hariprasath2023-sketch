package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Response.BorrowResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private BorrowService userHistoryService;
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserBook(@PathVariable Long userId) {
        List<BorrowResponse> userBookList = userHistoryService.findById(userId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(userBookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse>returnBooks(@PathVariable Long id){
        String rBOOK= userHistoryService.returnBooks(id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(rBOOK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
