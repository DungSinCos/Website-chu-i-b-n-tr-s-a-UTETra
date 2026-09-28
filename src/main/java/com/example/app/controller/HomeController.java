package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("message", "Spring Boot + JSP + SiteMesh + Bootstrap is running!");
        return "home"; // -> /WEB-INF/views/home.jsp
    }
}
