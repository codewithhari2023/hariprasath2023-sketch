package OnlineEBookStore.demo.Controller.Admin;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RolesAllowed(Roles.ADMIN)
public class AdminUserController {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllUsers() {
        List<CommonUser> appusers = userService.findAllUsers();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(appusers);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
