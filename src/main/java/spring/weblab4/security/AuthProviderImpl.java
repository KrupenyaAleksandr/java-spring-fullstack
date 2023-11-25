package spring.weblab4.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import spring.weblab4.services.UserDetailsServiceImpl;

import java.util.Collections;

// реализуем интерфейс, аутенфикейшнпровайдер используется для аутентификации пользователей,
// при помощи метода аутентификейт, который использует объект класса Аутентификейшн, который в свою очередь
// хранит поля креденшиалс и принципал и возвращает их при помощи геттеров, проверяет данные пользователя
// и при успешной аутентификации возвращает объект Аутентефикейшн иначе выбрасывает эксепшн

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthProviderImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userLogin = authentication.getName();
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userLogin);
        String password = authentication.getCredentials().toString();
        if (!password.equals(userDetails.getPassword()))
            throw new BadCredentialsException("Некорректный пароль");
        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
