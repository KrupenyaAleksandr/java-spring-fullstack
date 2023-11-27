package spring.weblab4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import spring.weblab4.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity // говорим спрингу, что мы берём конфигурацию spring security на себя
public class SpringConfig{
    //private final AuthProviderImpl authProvider;

    //@Autowired
    //public SpringConfig(AuthProviderImpl authProvider) {
        //this.authProvider = authProvider;
    //}

    // передаём свою имплементацию юзердетейлс чтобы секьюрити мог работать с нашим типом данных юзер
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    public static final String[] endpoints_whitelist = {
            "/",
            "/login",
            "/registration"
    };

    @Autowired
    public SpringConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers(endpoints_whitelist).permitAll()
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll()
                );
        return http.build();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }
}
