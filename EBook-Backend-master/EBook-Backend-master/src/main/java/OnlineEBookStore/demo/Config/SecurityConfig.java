package OnlineEBookStore.demo.Config;
import OnlineEBookStore.demo.Service.UserDetailsServiceImpl;
import OnlineEBookStore.demo.Session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private SessionFilter sessionFilter;
    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


    public SecurityConfig(SessionFilter sessionFilter, AuthenticationEntryPoint authEntryPoint) {
        this.sessionFilter = sessionFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/api/auth/**",
                                        "/api/admin/book/downloadFile/**",
                                        "/api/barcode/generate",
                                        "/mysql-console/**",
                                        "/swagger-ui/**",
                                        "/ws/**",
                                        "http://localhost:8080/ws",
                                        "/api/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.invalidSessionUrl("/login")
                )
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions().disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, ex) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())
                                )
                );

        httpSecurity.addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
                .and()
                .invalidSessionUrl("/login?invalid=true")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("http://localhost:8080/api/auth/login")
                .permitAll()
                .and()
                .logout()

                .permitAll();


    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

//    @Bean
//    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
//        return new CustomSessionInformationExpiredStrategy();
//    }


}






