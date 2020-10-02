package com.lazyhippos.todolistapp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    // Show login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Show login form with Error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
