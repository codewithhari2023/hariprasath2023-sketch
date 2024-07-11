package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordChangeRequest {
    private  String username;
  private   String currentPassword;
    private String newPassword;
}
