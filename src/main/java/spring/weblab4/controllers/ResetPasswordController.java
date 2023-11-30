package spring.weblab4.controllers;

import jakarta.validation.Valid;
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
import spring.weblab4.services.SendEmailService;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ResetPasswordController {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final SendEmailService sendEmailService;

    public ResetPasswordController(UserRepository userRepository, ResetPasswordController resetPasswordController, PasswordTokenRepository passwordTokenRepository, SendEmailService sendEmailService) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.sendEmailService = sendEmailService;
    }

    @GetMapping("/reset-password")
    public String resetPassword(@ModelAttribute("user") User user){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String performResetPassword(@RequestParam("username") String username,
                                       @ModelAttribute("user") @Valid Optional<User> user,
                                       BindingResult bindingResult){
        user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            bindingResult.rejectValue("username", "username", "error");
            return "reset-password";
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        //TODO
        return "redirect:/";
    }

    @PostMapping("/perform-reset-password")
    private void createPasswordResetTokenForUser(@Valid Optional<User> user, String token){
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);

    }
}
