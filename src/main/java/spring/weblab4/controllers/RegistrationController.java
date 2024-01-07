package spring.weblab4.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;
import spring.weblab4.services.LogService;
import spring.weblab4.services.RegistrationService;
import spring.weblab4.util.UserValidator;

import java.util.List;

@Controller
public class RegistrationController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final LogService logService;

    public RegistrationController(UserValidator userValidator, RegistrationService registrationService, LogService logService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.logService = logService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      @RequestParam("confirmUsername") String confirmUsername,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      RedirectAttributes redirectAttributes,
                                      BindingResult bindingResult) throws Exception {
        if (!user.getUsername().equals(confirmUsername)){
            redirectAttributes.addFlashAttribute("confirmUsernameError", "Электронная почта не совпадает.");
            return "redirect:registration";
        }

        if (!user.getPassword().equals(confirmPassword)){
            redirectAttributes.addFlashAttribute("confirmPasswordError", "Пароль не совпадает.");
            return "redirect:registration";
        }

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("username")){
                redirectAttributes.addFlashAttribute("usernameError", "Пользователь уже существует.");
            }
            if (bindingResult.hasFieldErrors("password")){
                redirectAttributes.addFlashAttribute("passwordError", "Пароль не соответствует условиям.");
            }
            return "redirect:registration";
        }

        registrationService.register(user);
        logService.publishLogEvent(user, new LogAction(10));
        return "redirect:/login";
    }
}
