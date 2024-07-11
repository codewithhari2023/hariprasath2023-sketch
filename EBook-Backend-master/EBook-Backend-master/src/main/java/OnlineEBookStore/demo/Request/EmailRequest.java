package OnlineEBookStore.demo.Request;

import com.sendgrid.helpers.mail.objects.Email;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRequest {
    private String email;
    private String subject;
    private String message;
}
