package spring.weblab4.services;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import spring.weblab4.models.User;

import java.util.Locale;

@Service
public class SendEmailService {

    private final MessageSource messageSource;

    public SendEmailService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale,
                                                      String token, User user)
    {
        String url = contextPath + "reset-password-process?token=" + token;
        String message = messageSource.getMessage("message.reset-password", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getUsername());
        email.setFrom("elshurimem1337@gmail.com");
        return email;
    }
}
