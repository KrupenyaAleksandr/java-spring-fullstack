package spring.weblab4.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.weblab4.models.PasswordResetToken;

import java.util.Calendar;

@Component
public class PasswordTokenValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordResetToken.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordResetToken passwordResetToken = (PasswordResetToken) target;
        if (!isTokenExpired(passwordResetToken))
            return;
        errors.rejectValue("expire_date", "", "Expired token");
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar calendar = Calendar.getInstance();
        return passToken.getExpire_date().before(calendar);
    }
}
