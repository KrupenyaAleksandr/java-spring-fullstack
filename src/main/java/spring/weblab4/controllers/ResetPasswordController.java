package spring.weblab4.controllers;

import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import spring.weblab4.util.UserValidator;

import java.util.*;

@Controller
public class ResetPasswordController {
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final EmailService emailService;
    private final JavaMailSenderImpl mailSender;
    private final PasswordTokenValidator passwordTokenValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Lazy
    public ResetPasswordController(UserRepository userRepository, ResetPasswordController resetPasswordController,
                                   PasswordTokenRepository passwordTokenRepository, EmailService emailService,
                                   JavaMailSenderImpl mailSender, PasswordTokenValidator passwordTokenValidator, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.mailSender = mailSender;
        this.passwordTokenValidator = passwordTokenValidator;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @GetMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String performResetPassword(@RequestParam("username") String username,
                                       RedirectAttributes redirectAttributes){
        Optional<User> tmpUser = userRepository.findByUsername(username);
        if (tmpUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Пользователь не был найден");
            return "redirect:reset-password";
        }
        User user = tmpUser.get();
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(token, user);
        mailSender.send(emailService.constructResetTokenEmail(token, user.getUsername()));
        redirectAttributes.addFlashAttribute("message", "Сообщение было отправлено на почту");
        return "redirect:reset-password";
    }

/*    @GetMapping("/perform-reset-password-from-profile")
    public String performResetPasswordFromProfile(@RequestParam("username") String username,
                                       RedirectAttributes redirectAttributes){
        System.out.println(username);
        User user = userRepository.findByUsername(username).get();
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(token, user);
        mailSender.send(emailService.constructResetTokenEmail(token, user.getUsername()));
        redirectAttributes.addFlashAttribute("message", "Сообщение было отправлено на почту");
        return "redirect:my-profile";
    }*/

    @GetMapping("/set-password")
    public String setPasswordPage(@RequestParam ("token") String token, RedirectAttributes redirectAttributes,
                                  @ModelAttribute("passwordresettoken") @Valid PasswordResetToken passwordResetToken,
                                  BindingResult bindingResult, Model model){
        Optional<PasswordResetToken> myToken = passwordTokenRepository.findPasswordResetTokenByToken(token);
        if (myToken.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "Неправильный токен.");
            return "redirect:reset-password";
        }
        passwordTokenValidator.validate(myToken.get(), bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Токен просрочен.");
            return "redirect:reset-password";
        }
        model.addAttribute("token", token);
        return "set-password";
    }

    @PostMapping("/set-password")
    public String setPassword(@RequestParam("token") String token, @RequestParam("password") String password,
                              Model model, RedirectAttributes redirectAttributes) {
        if (userValidator.checkPassword(password)){
            redirectAttributes.addFlashAttribute("message", "Пароль не соответствует условиям.");
            return "redirect:set-password" + "?token=" + token;
        }
        PasswordResetToken passToken = passwordTokenRepository.findPasswordResetTokenByToken(token).get();
        User user = passToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        passwordTokenRepository.deleteById(passToken.getId());
        return "redirect:login";
    }
    private void createPasswordResetTokenForUser(String token, User user){
        Optional<PasswordResetToken> myToken = passwordTokenRepository.findPasswordResetTokenByUserId(user.getId());
        if (!myToken.isEmpty()){
            PasswordResetToken changeExistToken = myToken.get();
           changeExistToken.setToken(token);
           Calendar calendar = Calendar.getInstance();
           calendar.add(Calendar.HOUR_OF_DAY, 1);
           changeExistToken.setExpire_date(calendar);
            passwordTokenRepository.save(changeExistToken);
            return;
        }
        PasswordResetToken newToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(newToken);
    }
}