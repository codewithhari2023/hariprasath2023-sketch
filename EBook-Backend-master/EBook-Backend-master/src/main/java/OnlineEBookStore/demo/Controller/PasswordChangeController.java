package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Request.PasswordChangeRequest;
import OnlineEBookStore.demo.Service.PasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pass")
public class PasswordChangeController {
    @Autowired
    private PasswordChangeService passwordChangeService;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        boolean passwordChanged = passwordChangeService.changePassword(passwordChangeRequest.getUsername(),passwordChangeRequest.getCurrentPassword(),passwordChangeRequest.getNewPassword());

        if (passwordChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or current password");
        }
    }
}
