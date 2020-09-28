package com.lazyhippos.todolistapp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    String showLoginPage(){
        return "login";
    }
}
