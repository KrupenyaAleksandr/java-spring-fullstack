package spring.weblab4.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.weblab4.models.User;
import spring.weblab4.services.ValidateUserService;

@Component
public class UserValidator implements Validator {

    private final ValidateUserService validateUserService;

    @Autowired
    public UserValidator(ValidateUserService validateUserService) {
        this.validateUserService = validateUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (validateUserService.isUserAlreadyExists(user.getUsername()))
            return;
        errors.rejectValue("username", "", "Пользователь с такой почтой уже существует");
    }
}
