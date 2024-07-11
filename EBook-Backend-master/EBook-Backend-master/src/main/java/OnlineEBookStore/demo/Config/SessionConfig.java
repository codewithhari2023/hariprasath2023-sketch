package OnlineEBookStore.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSessionListener;

@Configuration
public class SessionConfig {

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionEventPublisher();
    }
}