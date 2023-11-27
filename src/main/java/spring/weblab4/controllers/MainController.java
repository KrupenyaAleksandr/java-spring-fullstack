package spring.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/home")
    public String homePage1(){
        return "home";
    }
}
