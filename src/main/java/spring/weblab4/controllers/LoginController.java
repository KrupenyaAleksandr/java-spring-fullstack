package spring.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/process-login")
    public String performLogin(){
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String performLogout(){
        return "redirect:login";
    }
}
