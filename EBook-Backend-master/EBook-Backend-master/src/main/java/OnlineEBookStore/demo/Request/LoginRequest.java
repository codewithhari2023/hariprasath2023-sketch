package OnlineEBookStore.demo.Request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
