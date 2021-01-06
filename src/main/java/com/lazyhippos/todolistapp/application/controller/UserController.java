package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.ImageRequest;
import com.lazyhippos.todolistapp.application.resource.UserProfile;
import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.application.resource.UserUpdateRequest;
import com.lazyhippos.todolistapp.domain.model.Users;
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
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;
    private final String REDIRECT = "redirect:";
    private final String USER_REGISTER_VIEW = "signup";
    private final String USER_EDIT_VIEW = "editUser";
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

    @GetMapping("/user/profile/edit")
    public String showProfileEditPage (Model model, Principal principal) {
        if (!isAuthenticated()) {
            return REDIRECT + SLASH;
        }
        if (principal == null) {
            return REDIRECT + SLASH;
        }
        Users user = userService.retrieveAuthorProfile(principal.getName());
        // Fetch basic user profile
        UserProfile userProfile = new UserProfile(
                user.getUserId(),
                user.getDisplayName(),
                user.getImageId(),
                user.getActive()
        );

        model.addAttribute("request", new UserUpdateRequest(
                user.getUserId(),
                user.getDisplayName(),
                user.getEmailAddress(),
                user.getImageId()
        ));
        model.addAttribute("isLogin", true);
        model.addAttribute("loginUserId", user.getUserId());
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("imageRequest", new ImageRequest(null, principal.getName()));
        return USER_EDIT_VIEW;
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

    @PostMapping("/user/update")
    public String update (@ModelAttribute(name = "request") UserUpdateRequest request) {
        System.out.println("DEBUG: Received Post request for updating profile.");
            userService.update(request, LocalDateTime.now());
        return REDIRECT + SLASH + "m" + SLASH + request.getUserId();
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
