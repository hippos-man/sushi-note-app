package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;
    private final String REDIRECT = "redirect:";
    private final String USER_REGISTER_VIEW = "signup";
    private final String LOGIN_VIEW = "login";
    private final String SLASH = "/";


    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public String showUserRegisterPage(Model model){
        if (isAuthenticated()) {
            return REDIRECT + SLASH;
        }
        model.addAttribute("request", new UserRequest());
        return USER_REGISTER_VIEW;
    }


    @PostMapping("/user/register")
    public String register(@Valid @ModelAttribute(name = "request") UserRequest request,
                           BindingResult bindingResult, Model model){
        if (isAuthenticated()) {
            return REDIRECT + SLASH;
        }

        // Duplicate USER ID check
        if (!request.getUserId().isEmpty()) {
            Boolean isUserIdExist = userService.isUserIdExist(request.getUserId());
            if (isUserIdExist) {
                bindingResult.rejectValue("userId",
                        "error.user",
                        "A user id already exists.");
            }
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("request", request);
            return USER_REGISTER_VIEW;
        }

        // Get current time
        LocalDateTime now = LocalDateTime.now();

        // Store new user
        userService.register(request, now);
        return LOGIN_VIEW;
    }

    @GetMapping("/login")
    public String getUserLoginPage() {
        if (isAuthenticated()) {
            System.out.println("This user has already logged in");
            return REDIRECT + SLASH;
        }
        return LOGIN_VIEW;
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
