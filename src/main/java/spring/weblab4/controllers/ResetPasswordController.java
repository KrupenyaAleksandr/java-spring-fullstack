package spring.weblab4.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.weblab4.models.PasswordResetToken;
import spring.weblab4.models.User;
import spring.weblab4.repositories.PasswordTokenRepository;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.services.EmailService;
import spring.weblab4.util.PasswordTokenValidator;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ResetPasswordController {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final EmailService emailService;
    private final JavaMailSenderImpl mailSender;
    private final PasswordTokenValidator passwordTokenValidator;

    @Lazy
    public ResetPasswordController(UserRepository userRepository, ResetPasswordController resetPasswordController,
                                   PasswordTokenRepository passwordTokenRepository, EmailService emailService,
                                   JavaMailSenderImpl mailSender, PasswordTokenValidator passwordTokenValidator) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.mailSender = mailSender;
        this.passwordTokenValidator = passwordTokenValidator;
    }

    @GetMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

    //создаём новый токен
    @PostMapping("/reset-password")
    public String performResetPassword(HttpServletRequest request,
                                       @RequestParam("username") String username,
                                       RedirectAttributes redirectAttributes){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Failed");
            return "redirect:reset-password";
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(token, user);
        mailSender.send(emailService.constructResetTokenEmail(request.getLocale(), token, user.get().getUsername()));
        redirectAttributes.addFlashAttribute("message", "Success");
        return "redirect:reset-password";
    }

    @GetMapping("/set-password")
    public String setPasswordPage(@RequestParam ("token") String token, RedirectAttributes redirectAttributes,
                                  @ModelAttribute("passwordresettoken") @Valid PasswordResetToken passwordResetToken,
                                  BindingResult bindingResult){
        Optional<PasswordResetToken> myToken = passwordTokenRepository.findPasswordResetTokenByToken(token);
        if (myToken.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "token not found");
            return "redirect:reset-password";
        }
        passwordTokenValidator.validate(myToken.get(), bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "expired token");
            return "redirect:reset-password";
        }
        return "set-password";
    }

    @PostMapping("/set-password")
    public String setPassword(@ModelAttribute("passwordresettoken")@Valid PasswordResetToken passwordResetToken,
                              @RequestParam("token") String token, BindingResult bindingResult)
    {
        return "redirect:login";
    }
    private void createPasswordResetTokenForUser(String token, Optional<User> user){
        Optional<PasswordResetToken> myToken = passwordTokenRepository.findPasswordResetTokenByUserId(user.get().getId());
        if (!myToken.isEmpty()){
            PasswordResetToken changeExistToken = myToken.get();
           changeExistToken.setToken(token);
           Calendar calendar = Calendar.getInstance();
           calendar.add(Calendar.HOUR_OF_DAY, 1);
           changeExistToken.setExpire_date(calendar);
            passwordTokenRepository.save(changeExistToken);
            return;
        }
        PasswordResetToken newToken = new PasswordResetToken(token, user.get());
        passwordTokenRepository.save(newToken);
    }
}