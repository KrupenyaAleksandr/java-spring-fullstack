package spring.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {
    @GetMapping("")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin-page";
    }
}
