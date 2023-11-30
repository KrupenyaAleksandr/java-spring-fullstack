package spring.weblab4.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.weblab4.models.User;

import java.util.Collection;
import java.util.Collections;

// реализуем интерфейс UserDetails, переопределяем методы
// чтобы они возвращали информацию о нашем entity юзер, пароль, логин, действительность акка
// прослойка между репозиторием и остальным spring приложением видимо,
// потому что реализует в своих методах просто геттеры из репозитория
// попробовал добавить getEmail()
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // для получения данных аутентифицированного пользователя
    public User getUser(){
        return this.user;
    }
}
