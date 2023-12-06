package spring.weblab4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.weblab4.models.User;
import spring.weblab4.util.EventPublisher;

@Controller
public class LoginController {

    @Autowired
    EventPublisher eventPublisher;

    @GetMapping("/login")
    public String loginPage(){
        if (isAuthenticated())
            return "redirect:home";
        return "login";
    }

    @PostMapping("login")
    public String performLogin(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        System.out.println(1);
        System.out.println(user.getUsername());
        //eventPublisher.publishLogEvent();
        return "redirect:home";
    }

    @PostMapping("/logout")
    public String performLogout(){
        return "redirect:login";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
