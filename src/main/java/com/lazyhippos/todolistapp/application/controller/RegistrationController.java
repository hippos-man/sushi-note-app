package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") final String token) {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            // TODO Do something
            System.out.println("Verification Token is valid. Your account is successfully activated.");
        }
        return "redirect:/";
    }
}
