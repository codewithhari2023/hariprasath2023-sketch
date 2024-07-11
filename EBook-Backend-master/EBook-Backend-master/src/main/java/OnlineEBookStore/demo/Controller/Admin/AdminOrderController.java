package OnlineEBookStore.demo.Controller.Admin;

import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Model.Status;
import OnlineEBookStore.demo.Request.OrderStatusRequest;
import OnlineEBookStore.demo.Response.OrderResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/admin/order")
@RolesAllowed(Roles.ADMIN)
public class AdminOrderController {
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private OrderService orderService;
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getallorders(){
        List<OrderResponse> orderResponses=orderService.getAllOrders();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderResponses);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/status/all")
    public ResponseEntity<APIResponse>getallorderstatus(){
        List<Status>orderResponses=orderService.getAllOrderStatus();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderResponses);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PutMapping("/status")
    public ResponseEntity<APIResponse>updateorderstatus(@RequestBody OrderStatusRequest orderStatusRequest){
        List<OrderResponse>orderlist=orderService.updateOrderStatus(orderStatusRequest.getOrderId(),orderStatusRequest.getStatusId());
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderlist);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse>getuserorders(@PathVariable Long Id){
        List<OrderResponse>orderList=orderService.getUserOrders(Id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(orderList);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
