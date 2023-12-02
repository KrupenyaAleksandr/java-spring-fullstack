package spring.weblab4.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.services.ValidateService;

import java.util.Optional;

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

        if (isUserAlreadyExists(user.getUsername()))
            return;
        errors.rejectValue("username", "", "Пользователь с такой почтой уже существует");
    }

    private boolean isUserAlreadyExists(String s){
        Optional<User> user = userRepository.findByUsername(s);
        return user.isEmpty();
    }
}
