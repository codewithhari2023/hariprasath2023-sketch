package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Roles;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class RoleRegisterResponse {
    @NotEmpty
    private String username;
    @NotEmpty
    private  String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private Roles role;
}
