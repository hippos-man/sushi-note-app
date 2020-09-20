package com.lazyhippos.todolistapp.application;

import com.lazyhippos.todolistapp.domain.dto.Todos;
import com.lazyhippos.todolistapp.domain.dto.Users;
import com.lazyhippos.todolistapp.domain.model.TodoRequest;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TodosController {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @PostMapping("/register")
    public String registerTodo (){

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

        // Mock request body
        TodoRequest mockTodoRequest = new TodoRequest(
          "5i4jgahgja",
          "Go to Rahmen shop near my house",
          null,
          null,
          false,
          LocalDateTime.now(),
          null,
          null,
          "john777"
        );

        // Convert to Entity
        Todos todosEntity = new Todos(
                mockTodoRequest.getTodoId(),
                mockTodoRequest.getTitle(),
                mockTodoRequest.getDescription(),
                null,
                mockTodoRequest.getCompleted(),
                mockTodoRequest.getCreatedDateTime().toString(),
                null,
                mockTodoRequest.getLabelId(),
                mockTodoRequest.getUserId()
        );
        // Save request
        todoJpaRepository.save(todosEntity);
        return "redirect:/";
    }

    @GetMapping("/")
    public String listTodos(Model model){

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
        model.addAttribute("todos", todos);
        return "index";
    }
}
