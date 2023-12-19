package spring.weblab4.util;

import jakarta.persistence.criteria.CollectionJoin;
import org.passay.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String>{
    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                //TODO : check password length before encoding
                new LengthRule(4, 256),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        String constraint = "";
        for (String message : validator.getMessages(result)){
            message += ",";
            constraint += message;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(constraint)
                .addConstraintViolation();
        return false;
    }
}
