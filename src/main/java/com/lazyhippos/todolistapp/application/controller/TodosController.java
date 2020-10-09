package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.TodoLabelRequest;
import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Labels;
import com.lazyhippos.todolistapp.domain.model.TodoLabel;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.service.LabelService;
import com.lazyhippos.todolistapp.domain.service.TodoLabelService;
import com.lazyhippos.todolistapp.domain.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/to-do")
@Controller
public class TodosController {

    private final TodoService todoService;
    private final LabelService labelService;
    private final TodoLabelService todoLabelService;

    TodosController (TodoService todoService, LabelService labelService, TodoLabelService todoLabelService){
        this.todoService = todoService;
        this.labelService = labelService;
        this.todoLabelService = todoLabelService;
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

    @PostMapping("/complete/{todoId}")
    public String completeTodo(@PathVariable("todoId") String todoId){
        todoService.complete(todoId);
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

        // Retrieve all labels by User ID
        List<Labels> labelsList = labelService.retrieveAll(loginUserID);

        // Add to Model
        model.addAttribute("todos", incompleteTasks);
        model.addAttribute("labels", labelsList);
        model.addAttribute("todoForm", new TodoRequest());
        return "index";
    }

    @GetMapping("/{todoId}/detail")
    public String showTaskDetail(@PathVariable("todoId") String todoId, Model model){
        // Retrieve the object by To-do ID
        Todos todo= todoService.retrieveOne(todoId);
        // Retrieve all labels that login user created
        List<Labels> allLabels = labelService.retrieveAll(todo.getUserId());
        // Generate Request
        TodoLabelRequest request = new TodoLabelRequest(
                allLabels,
                todoId
        );
        // Fetch all related labelId
        List<TodoLabel> todoLabelList = todoLabelService.retrieveALlLabelId(todoId);
        List<String> labelIdList = new ArrayList<>();
        for (TodoLabel todoLabel: todoLabelList) {
            labelIdList.add(todoLabel.getLabelId());
        }
        List<Labels> relatedLabels = labelService.retrieveByLabelIds(labelIdList);
        model.addAttribute("request", TodoRequest.generateTodoRequest(todo));
        model.addAttribute("relatedLabels", relatedLabels);
        model.addAttribute("todoLabelRequest", request);
        return "todoDetail";
    }
}
