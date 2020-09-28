package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/register")
    public String showUserRegisterPage(Model model){
        model.addAttribute("request", new UserRequest());
        return "register";
    }


    @PostMapping("/user/register")
    public String register(@ModelAttribute UserRequest request){

        // Get current time
        LocalDateTime now = LocalDateTime.now();

        // Store new user
        userService.register(request, now);
        return "redirect:/";
    }
}
