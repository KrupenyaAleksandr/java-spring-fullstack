package spring.weblab4.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailService{

    public SimpleMailMessage constructResetTokenEmail(Locale locale,
                                                      String token, String userEmail)
    {
        String url = "localhost:8080/reset-password-process?token=" + token;
        String message = "Reset password";
        return constructEmail("Reset Password", message + " \r\n" + url, userEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String userEmail){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(userEmail);
        email.setFrom("myevernotesite@gmail.com");
        return email;
    }
}
