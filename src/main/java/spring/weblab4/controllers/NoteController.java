package spring.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class NoteController {
    @GetMapping("/home")
    public String home(Principal principal, Model model){
        model.addAttribute(principal);
        return "home";
    }
}
