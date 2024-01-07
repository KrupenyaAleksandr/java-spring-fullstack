package spring.weblab4.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (isUserAlreadyExists(user.getUsername())){
            errors.rejectValue("username", "", "Пользователь уже существует.");
        }

        if (checkPassword(user.getPassword())){
            errors.rejectValue("password", "", "Пароль не соответствует условиям.");
        }
    }

    private boolean isUserAlreadyExists(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return !user.isEmpty();
    }

    public boolean checkPassword(String password){
        String regex = "^(?=\\S+$).{4,20}$";
        Pattern pattern = Pattern.compile(regex);
        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);

        return !matcher.matches();
    }
}
