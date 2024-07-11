package OnlineEBookStore.demo.Controller.Admin;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Request.RegisterRequest;
import OnlineEBookStore.demo.Request.RoleRegisterRequest;
import OnlineEBookStore.demo.Request.RoleRequest;
import OnlineEBookStore.demo.Response.AuthResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Response.RoleRegisterResponse;
import OnlineEBookStore.demo.Response.RoleResponse;
import OnlineEBookStore.demo.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/admin/role")
@RolesAllowed(Roles.ADMIN)
public class AdminRoleController {
@Autowired
   private RoleService roleService;
@Autowired
   private APIResponse apiResponse;

    @PostMapping("/addRole")
    public ResponseEntity<APIResponse> Register(@RequestBody RoleRequest roleRequest){
        RoleResponse roleResponse=roleService.addRole(roleRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(roleResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/registerRole")
    public ResponseEntity<APIResponse>Register(@RequestBody RoleRegisterRequest roleRegisterRequest){
        RoleRegisterResponse roleRegisterResponse=roleService.register(roleRegisterRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(roleRegisterResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/{RoleId}")
    public ResponseEntity<APIResponse>getRole(@PathVariable Long id){
        RoleResponse roleRegisterResponse=roleService.getRoles(id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(roleRegisterResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllUsers() {
        List<Roles> roles = roleService.findall();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(roles);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
