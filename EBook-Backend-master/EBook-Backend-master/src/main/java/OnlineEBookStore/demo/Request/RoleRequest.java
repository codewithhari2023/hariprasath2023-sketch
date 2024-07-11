package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleRequest {
    private Long id;
    @NotEmpty
    private String role;
}
