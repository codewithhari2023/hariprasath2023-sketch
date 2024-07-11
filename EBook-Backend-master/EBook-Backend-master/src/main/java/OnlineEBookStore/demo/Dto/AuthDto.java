package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.RegisterRequest;
import OnlineEBookStore.demo.Response.AuthResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthDto {

    public CommonUser MapToUser(RegisterRequest registerRequest) {
        CommonUser commonUser = new CommonUser();
        commonUser.setUsername(registerRequest.getUsername());
        commonUser.setPassword(registerRequest.getPassword());
        commonUser.setName(registerRequest.getName());
        return commonUser;
    }

    public AuthResponse MapToAuthResponse(CommonUser commonUser) {


            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(commonUser.getId());
            authResponse.setUsername(commonUser.getUsername());
            authResponse.setName(commonUser.getName());
            authResponse.setPassword(commonUser.getPassword());
            authResponse.setRole(commonUser.getRole());
            return authResponse;



    }
}

