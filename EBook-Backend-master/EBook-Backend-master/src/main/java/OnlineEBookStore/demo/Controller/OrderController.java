package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.DebitCard;
import OnlineEBookStore.demo.Request.OrderRequest;
import OnlineEBookStore.demo.Response.OrderResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderResponse> orderList = orderService.getUserOrders(userId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        DebitCard debitCard=new DebitCard();
            List<OrderResponse> orderList = orderService
                    .placeOrder(orderRequest.getUserId(),orderRequest.getAddressId());
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(orderList);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);



        }
    }
