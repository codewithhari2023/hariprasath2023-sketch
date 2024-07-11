package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.Roles;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleRegisterRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private  String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private Roles role;
}
