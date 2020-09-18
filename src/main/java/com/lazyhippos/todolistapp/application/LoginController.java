package com.lazyhippos.todolistapp.application;

import com.lazyhippos.todolistapp.domain.dto.Users;
import com.lazyhippos.todolistapp.domain.repository.UserJpaRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class LoginController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping(path = "/all")
    public @ResponseBody List<Users> getAllUsers() {
        return userJpaRepository.findAll();
    }
}
