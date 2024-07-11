package OnlineEBookStore.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping("/status")
    public ResponseEntity<String> getSessionStatus(HttpServletRequest request) {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessi{on expired. Redirecting to login page.");
        }
        return ResponseEntity.ok("Session active");
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED).build();
    }
}