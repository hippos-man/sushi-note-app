package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class TodosController {

    private final TodoService todoService;

    TodosController (TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/task/register")
    public String registerTask (Principal principal, @ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        String loginUserId = principal.getName();
        // Store new object
        todoService.store(request.getTitle(), currentDatetime, loginUserId);
        return "redirect:/";
    }


    @PostMapping("/task/update/{todoId}")
    public String updateTask(@PathVariable("todoId") String todoId, @ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        // Update
        todoService.update(todoId, request, currentDatetime);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showTaskList(Principal principal, Model model){
        String loginUserID = principal.getName();
        // Retrieve all Todos by user ID
        List<Todos> todos = todoService.retrieveAll(loginUserID);
        // Add to Model
        model.addAttribute("todos", todos);
        model.addAttribute("todoForm", new TodoRequest());
        return "index";
    }

    @GetMapping("/task/detail/{todoId}")
    public String showTaskDetail(@PathVariable("todoId") String todoId, Model model){
        // Retrieve the object by To-do ID
        Todos todo= todoService.retrieveOne(todoId);
        model.addAttribute("request", TodoRequest.generateTodoRequest(todo));
        return "todoDetail";
    }
}
