package spring.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin_page";
    }
}
