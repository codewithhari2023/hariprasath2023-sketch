package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Request.EmailRequest;
import OnlineEBookStore.demo.Service.EmailService;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        String Email = emailRequest.getEmail();
        String subject = emailRequest.getSubject();
        String body = emailRequest.getMessage();
        emailService.sendSimpleEmail(Email,subject, body);

        return "Email sent successfully!";
    }
}
