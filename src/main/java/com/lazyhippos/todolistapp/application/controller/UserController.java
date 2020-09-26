package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
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


    @PostMapping("/user/register")
    public void register(){

        // Get current time
        LocalDateTime now = LocalDateTime.now();
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
