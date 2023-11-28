package spring.weblab4.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.weblab4.models.User;
import spring.weblab4.services.RegistrationService;
import spring.weblab4.util.UserValidator;

@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "registration";
    }

    //bindingresult нужен в случае если произойдёт ошибка при проверке user, поместить
    //ошибку в байндингрезульт и затем обработать в программе
    //modelattribute нужен для того чтобы создать новый объект и положить в него данные из формы
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) throws Exception {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        registrationService.register(user);
        return "redirect:/login";
    }

    @PostMapping("/process_login")
    public String performLogin(){
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String performLogout(){
        return "redirect:/";
    }
}
