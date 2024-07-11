package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Borrow;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.DebitCard;
import OnlineEBookStore.demo.Model.Notification;
import OnlineEBookStore.demo.Request.BorrowRequest;
import OnlineEBookStore.demo.Request.NotificationRequest;
import OnlineEBookStore.demo.Request.OrderRequest;
import OnlineEBookStore.demo.Response.BorrowResponse;
import OnlineEBookStore.demo.Response.OrderResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BorrowService;
import OnlineEBookStore.demo.Service.OrderService;
import OnlineEBookStore.demo.Service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private PenaltyService penaltyService;

    @PostMapping("/send")
    public ResponseEntity<APIResponse> Request(@RequestBody
                                               NotificationRequest notificationRequest) {
        List<Notification> notifications = borrowService.Request(notificationRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(notifications);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> clear(@PathVariable Long id) {
        String RequestBook =borrowService.returnBooks(id);
        System.out.println(RequestBook);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(RequestBook);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/message")
    public ResponseEntity<APIResponse> getNotification() {
        List<Notification> notifications =borrowService.getNotification();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(notifications);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    }

