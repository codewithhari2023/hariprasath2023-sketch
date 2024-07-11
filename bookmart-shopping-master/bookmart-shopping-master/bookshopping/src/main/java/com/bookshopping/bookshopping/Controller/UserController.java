package com.bookshopping.bookshopping.Controller;

import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Request.AddressRequest;
import com.bookshopping.bookshopping.Response.AddressResponse;
import com.bookshopping.bookshopping.Response.RegularResponse.APIResponse;
import com.bookshopping.bookshopping.Service.Addressservice;
import com.bookshopping.bookshopping.Service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/user")
@RolesAllowed(Role.USER)
public class UserController
{
    @Autowired
    private Userservice userservice;
    @Autowired
    private Addressservice addressservice;
    @Autowired
    private APIResponse apiResponse;
@GetMapping("/{userId}")
    public ResponseEntity<APIResponse>getUserDetails(@PathVariable Integer userId){
    AppUser appuser=userservice.findByuserId(Long.valueOf(userId));
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(appuser);
    return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
    @PostMapping("/address")
    public ResponseEntity<APIResponse>createaddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressservice.create(addressRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse.getAddressList());
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PutMapping("/address/update")
    public ResponseEntity<APIResponse>updateaddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressservice.update(addressRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse.getAddressList());
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping("/address/{id}")
    public ResponseEntity<APIResponse>deleteaddress(@PathVariable Long id) {
        AddressResponse addressResponse = addressservice.deleteById(id);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(addressResponse.getAddressList());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
