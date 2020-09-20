package com.lazyhippos.todolistapp.application;

import com.lazyhippos.todolistapp.domain.dto.Todos;
import com.lazyhippos.todolistapp.domain.dto.Users;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TodosController {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @GetMapping("/")
    public List<Todos> get(Model model){

        // Mock Login user
        Users mockLoginUser = new Users (
                "john777",
                "John",
                "Smith",
                "hoge@gmail.com",
                "password",
                true,
                LocalDateTime.parse("2020-10-10T01:01:01"),
                null
        );

        // Retrieve all Todos by user ID
        List<Todos> todos = todoJpaRepository.findByUserId(mockLoginUser.getUserId());

        // Add to Model
//        model.addAttribute("todo", todos);
        return todos;
    }
}
