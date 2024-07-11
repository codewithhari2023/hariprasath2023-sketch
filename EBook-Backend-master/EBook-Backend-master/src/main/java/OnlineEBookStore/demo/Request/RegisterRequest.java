package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.Roles;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
  private String username;
  private  String name;
  private String password;
  private boolean mfaEnabled;
}
