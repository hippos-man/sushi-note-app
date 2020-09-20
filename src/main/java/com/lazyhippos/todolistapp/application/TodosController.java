package com.lazyhippos.todolistapp.application;

import com.lazyhippos.todolistapp.domain.dto.Todos;
import com.lazyhippos.todolistapp.domain.dto.Users;
import com.lazyhippos.todolistapp.domain.model.TodoRequest;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class TodosController {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @PostMapping("/register")
    public String registerTodo (@ModelAttribute TodoForm form){

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

        // Generate UUID
        String todoId = UUID.randomUUID().toString();

        // Request
        TodoRequest request = new TodoRequest(
                todoId,
          form.getTitle(),
          null,
          null,
          false,
          LocalDateTime.now(),
          null,
          null,
          mockLoginUser.getUserId()
        );

        // Convert to Entity
        Todos todosEntity = new Todos(
                request.getTodoId(),
                request.getTitle(),
                request.getDescription(),
                null,
                request.getCompleted(),
                request.getCreatedDateTime().toString(),
                null,
                request.getLabelId(),
                request.getUserId()
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
        model.addAttribute("todoForm", new TodoForm());
        return "index";
    }
}
