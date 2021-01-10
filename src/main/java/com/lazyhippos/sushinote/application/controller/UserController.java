package com.lazyhippos.sushinote.application.controller;

import com.lazyhippos.sushinote.application.resource.ImageRequest;
import com.lazyhippos.sushinote.application.resource.UserProfile;
import com.lazyhippos.sushinote.application.resource.UserRequest;
import com.lazyhippos.sushinote.application.resource.UserUpdateRequest;
import com.lazyhippos.sushinote.domain.model.Users;
import com.lazyhippos.sushinote.domain.service.UserService;
import com.lazyhippos.sushinote.registration.OnRegistrationCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final String REDIRECT = "redirect:";
    private final String USER_REGISTER_VIEW = "signup";
    private final String USER_EDIT_VIEW = "editUser";
    private final String LOGIN_VIEW = "login";
    private final String SLASH = "/";
    public final String MESSAGE_VIEW = "simple";


    UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
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
    public ModelAndView register(@Valid @ModelAttribute(name = "request") UserRequest request,
                                 BindingResult bindingResult, ModelMap model, HttpServletRequest servletRequest, RedirectAttributes ra){
        if (isAuthenticated()) {
            return new ModelAndView(REDIRECT + SLASH);
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

        // Duplicate Email Address check
        if (!request.getEmailAddress().isEmpty()) {
            Boolean isEmailAddressExist = userService.isEmailAddressExist(request.getEmailAddress());
            if (isEmailAddressExist) {
                bindingResult.rejectValue("emailAddress",
                        "error.user",
                        "A email address is registered already.");
            }
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("request", request);
            return new ModelAndView(USER_REGISTER_VIEW, model);
        }

        // Get current time
        LocalDateTime now = LocalDateTime.now();

        // Store new user (with disabled status)
        Users registered = userService.register(request, now);

        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(
                        registered, servletRequest.getLocale(), getAppUrl(servletRequest))
        );
        model.addAttribute("message",
                "Verification email is sent to your email address. Please activate your account.");
        return new ModelAndView(MESSAGE_VIEW, model);
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

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
