package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.AddressRequest;
import OnlineEBookStore.demo.Response.AddressResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.AddressService;
import OnlineEBookStore.demo.Service.UserService;
import OnlineEBookStore.demo.Service.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class UserController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceimpl userServiceimpl;
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse>getUserDetails(@PathVariable Long userId){
        CommonUser address=userService.findOneUSer(userId);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(address);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<APIResponse> createaddress(@RequestBody AddressRequest addressRequest){
        List<AddressResponse> addressResponse = addressService.create(addressRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PutMapping("/address/update")
    public ResponseEntity<APIResponse>updateaddress(@RequestBody AddressRequest addressRequest){
        List<AddressResponse> addressResponse = addressService.update(addressRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse>deleteaddress(@PathVariable Long id) {
        List<AddressResponse> addressResponse = addressService.deleteById(id);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
