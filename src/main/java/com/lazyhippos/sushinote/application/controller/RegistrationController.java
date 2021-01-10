package com.lazyhippos.sushinote.application.controller;

import com.lazyhippos.sushinote.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private final UserService userService;

    public final String MESSAGE_VIEW = "simple";

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confirmRegistration(@RequestParam("token") final String token, final ModelMap model) {
        final String result = userService.validateVerificationToken(token);
        String message = "";
        switch (result) {
            case "valid":
                message = "Your account is successfully activated.";
                break;
            case "invalidToken":
                message = "Verification Token is invalid. Please register again.";
                break;
            case "expired":
                message = "Verification Token is expired. Please register again.";
                break;
        }
        model.addAttribute("message", message);
        return new ModelAndView(MESSAGE_VIEW, model);
    }
}
