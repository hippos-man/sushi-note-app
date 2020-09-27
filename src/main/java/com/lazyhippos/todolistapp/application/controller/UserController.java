package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.UserRequest;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserJpaRepository userJpaRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @GetMapping("/get/all-user")
    public List<Users> get(){
        // DEBUG
        List<Users> userList = userJpaRepository.findAll();
        return userList;
    }

    @GetMapping("/user/register")
    public String showUserRegisterPage(Model model){
        model.addAttribute("request", new UserRequest());
        return "register";
    }


    @PostMapping("/user/register")
    public void register(@ModelAttribute UserRequest request){

        // Get current time
        LocalDateTime now = LocalDateTime.now();

        // TODO Bind Input
        // Mock input
        Users mockUserInput = new Users(
                "taro",
                "Taro",
                "Yamada",
                "fuga@outlook.com",
                "smith0123",
                true,
                now,
                now
        );

        // Encrypt password
//        String encryptedPassword = passwordEncoder.encode(mockUserInput.getPassword());
//        mockUserInput.setPassword(encryptedPassword);
//        System.out.println("The password is encrypted : " + encryptedPassword);
        // Execute
        userJpaRepository.save(mockUserInput);
        System.out.println("Sign up completed");
    }
}
