package spring.weblab4.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;
import spring.weblab4.services.LogService;
import spring.weblab4.services.RegistrationService;
import spring.weblab4.util.UserValidator;

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
                                      BindingResult bindingResult) throws Exception {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.register(user);
        logService.publishLogEvent(user, new LogAction(10));
        return "redirect:/login";
    }
}
