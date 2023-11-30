package spring.weblab4.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.weblab4.models.PasswordResetToken;
import spring.weblab4.models.User;
import spring.weblab4.repositories.PasswordTokenRepository;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.services.EmailService;
import spring.weblab4.util.GenericResponse;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ResetPasswordController {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final EmailService emailService;
    private final JavaMailSenderImpl mailSender;

    @Lazy
    public ResetPasswordController(UserRepository userRepository, ResetPasswordController resetPasswordController,
                                   PasswordTokenRepository passwordTokenRepository, EmailService emailService,
                                   JavaMailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.mailSender = mailSender;
    }

    @GetMapping("/reset-password")
    public String resetPassword(@ModelAttribute("user") User user){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String performResetPassword(HttpServletRequest request,
                                                @RequestParam("username") String username,
                                                @ModelAttribute("user") @Valid Optional<User> user,
                                                BindingResult bindingResult){
        user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            bindingResult.rejectValue("username", "username", "error");
            return "reset-password";
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(token, user);
        mailSender.send(emailService.constructResetTokenEmail(request.getLocale(), token, user.get().getUsername()));
        return "login";
    }

    private void createPasswordResetTokenForUser(String token, Optional<User> user){
        PasswordResetToken myToken = new PasswordResetToken(token, user.get());
        passwordTokenRepository.save(myToken);
    }
}