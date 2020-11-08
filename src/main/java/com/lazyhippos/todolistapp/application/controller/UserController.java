package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    private final String USER_REGISTER_VIEW = "signup";

    private final String LOGIN_VIEW = "login";

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String showUserRegisterPage(Model model){
        model.addAttribute("request", new UserRequest());
        return USER_REGISTER_VIEW;
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute(name = "request") UserRequest request, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("request", request);
            model.addAttribute("validationError", "Input value is not valid.");
            return USER_REGISTER_VIEW;
        }
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        // Store new user
        userService.register(request, now);
        return LOGIN_VIEW;
    }
}
