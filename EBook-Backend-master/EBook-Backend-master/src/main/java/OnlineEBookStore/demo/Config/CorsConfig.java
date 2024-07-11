package OnlineEBookStore.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    WebMvcConfigurer corsMvcconfigurer(){
        return new WebMvcConfigurer(){
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:4200").allowCredentials(true);
            }

        };

    }
}
