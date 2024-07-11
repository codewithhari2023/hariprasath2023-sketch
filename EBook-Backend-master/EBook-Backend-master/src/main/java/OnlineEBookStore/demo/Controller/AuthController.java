package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.LoginRequest;
import OnlineEBookStore.demo.Request.RegisterRequest;
import OnlineEBookStore.demo.Request.VerificationRequest;
import OnlineEBookStore.demo.Response.AuthResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.UserService;
import OnlineEBookStore.demo.Session.SessionRegistry;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
   private UserService userService;
@Autowired
 private APIResponse apiResponse;
    @Autowired
    public AuthenticationManager manager;
    @Autowired
    public SessionRegistry sessionRegistry;

@PostMapping("/register")
public ResponseEntity<APIResponse>Register(@RequestBody RegisterRequest registerRequest){
    AuthResponse commonloginuser=userService.register(registerRequest);
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setData(commonloginuser);
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        APIResponse apiResponse = new APIResponse();
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            final String sessionId = sessionRegistry.registerSession(loginRequest.getUsername());
            AuthResponse authResponse = userService.login(loginRequest, request);
            authResponse.setSessionId(sessionId);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(authResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            apiResponse.setMessage("An error occurred during login");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(userService.verifyCode(verificationRequest));
    }

}
