package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Roles;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class RoleResponse {
  private  Long id;
    @NotEmpty
    private String  role;
}
