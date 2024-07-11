package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Request.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;


    public void sendSimpleEmail(String to, String subject, String body)      {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("hariprasath0080@gmail.com");

        mailSender.send(message);
    }

}
