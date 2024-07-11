package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString

public class AuthResponse {
    private  Long id;
    private String username;
    private String password;
    private String name;
    private Roles role;
    private String sessionId;
    private boolean mfaEnabled;
    private String secretImageUri;

}
