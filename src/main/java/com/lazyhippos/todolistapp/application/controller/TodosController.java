package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TodosController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/register")
    public String register (@ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        // Mock Login user TODO Replace once login function is implemented
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
        // Store new object
        todoService.store(request.getTitle(), currentDatetime, mockLoginUser.getUserId());
        return "redirect:/";
    }


    @PostMapping("/update/{todoId}")
    public String updateTask(@PathVariable("todoId") String todoId, @ModelAttribute TodoRequest request){
        System.out.println("Will update Todo ID : " + todoId);
        System.out.println("title data from form is " + request.getTitle());
        // Update
        todoService.update(todoId, request);
        System.out.println("Redirect to index page");
        return "redirect:/";
    }

    @GetMapping("/")
    public String showTaskList(Model model){

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
        List<Todos> todos = todoService.retrieveAll(mockLoginUser.getUserId());
        // Add to Model
        model.addAttribute("todos", todos);
        model.addAttribute("todoForm", new TodoRequest());
        return "index";
    }

    @GetMapping("/detail/{todoId}")
    public String showTaskDetail(@PathVariable("todoId") String todoId, Model model){
        // Retrieve the object by To-do ID
        System.out.println(todoId);
        Todos todo= todoService.retrieveOne(todoId);
        System.out.println("Created date time is : " + todo.getCreatedDateTime());
        // TODO Extract this method to another class
        // Generate Update request from Entity
        TodoRequest request = new TodoRequest(
                todo.getTodoId(),
                todo.getTitle(),
             null,
                null,
                todo.getIsCompleted(),
                null,
                null,
               null
        );

        if(todo.getDescription() != null) {
            request.setDescription(todo.getDescription());
        }

        if(todo.getDeadlineDate() != null){
            request.setDeadlineDate(LocalDateTime.parse(todo.getDeadlineDate(),DateTimeFormatter.BASIC_ISO_DATE));
        }

        if(todo.getLabelId() != null) {
            request.setLabelId(todo.getLabelId());
        }

        model.addAttribute("request", request);
        return "todoDetail";
    }
}
