package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/to-do")
@Controller
public class TodosController {

    private final TodoService todoService;

    TodosController (TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/register")
    public String registerTodo (Principal principal, @ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        String loginUserId = principal.getName();
        // Store new object
        todoService.store(request.getTitle(), currentDatetime, loginUserId);
        return "redirect:/to-do/all";
    }


    @PostMapping("/update/{todoId}")
    public String updateTodo(@PathVariable("todoId") String todoId, @ModelAttribute TodoRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        // Update
        todoService.update(todoId, request, currentDatetime);
        return "redirect:/to-do/all";
    }

    @GetMapping("/all")
    public String listAll(Principal principal, Model model){
        String loginUserID = principal.getName();
        // Retrieve all tasks by User ID
        List<Todos> todos = todoService.retrieveAll(loginUserID);
        // Remove completed tasks
        List<Todos> incompleteTasks = todos.stream()
                .filter(t -> !t.getIsCompleted())
                .collect(Collectors.toList());
        // Add to Model
        model.addAttribute("todos", incompleteTasks);
        model.addAttribute("todoForm", new TodoRequest());
        return "index";
    }

    @GetMapping("/{todoId}/detail")
    public String showTaskDetail(@PathVariable("todoId") String todoId, Model model){
        // Retrieve the object by To-do ID
        Todos todo= todoService.retrieveOne(todoId);
        model.addAttribute("request", TodoRequest.generateTodoRequest(todo));
        return "todoDetail";
    }
}
