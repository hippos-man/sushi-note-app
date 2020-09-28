package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TodosController {

    private final TodoService todoService;

    TodosController (TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/task/register")
    public String registerTask (@ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        // Mock Login user TODO Replace once login function is implemented
        Users mockLoginUser = new Users (
                "john777",
                "John",
                "Smith",
                "password",
                true,
                LocalDateTime.parse("2020-09-09T01:01:01"),
                LocalDateTime.parse("2020-09-09T01:01:01")
        );
        // Store new object
        todoService.store(request.getTitle(), currentDatetime, mockLoginUser.getUserId());
        return "redirect:/";
    }


    @PostMapping("/task/update/{todoId}")
    public String updateTask(@PathVariable("todoId") String todoId, @ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        System.out.println("Will update Todo ID : " + todoId);
        System.out.println("title data from form is " + request.getTitle());
        // Update
        todoService.update(todoId, request, currentDatetime);
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
                "password",
                true,
                LocalDateTime.parse("2020-09-09T01:01:01"),
                null
        );

        // Retrieve all Todos by user ID
        List<Todos> todos = todoService.retrieveAll(mockLoginUser.getUserId());
        // Add to Model
        model.addAttribute("todos", todos);
        model.addAttribute("todoForm", new TodoRequest());
        return "index";
    }

    @GetMapping("/task/detail/{todoId}")
    public String showTaskDetail(@PathVariable("todoId") String todoId, Model model){
        // Retrieve the object by To-do ID
        Todos todo= todoService.retrieveOne(todoId);
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
            request.setDeadlineDate(todo.getDeadlineDate());
        }

        if(todo.getLabelId() != null) {
            request.setLabelId(todo.getLabelId());
        }

        model.addAttribute("request", request);
        return "todoDetail";
    }
}
