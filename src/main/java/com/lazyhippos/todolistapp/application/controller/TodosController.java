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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/list")
    public String showHome(@RequestParam(required = false) String label_id,
                           @RequestParam(required = false, defaultValue = "asc") String sort,
                           Principal principal, Model model,
                           @RequestParam(defaultValue = "false") Boolean isError){

        final String INDEX_VIEW = "index";

        String loginUserID = principal.getName();
        List<Todos> todos;
        if(label_id == null || label_id.equals("all")){
            todos = todoService.retrieveAll(loginUserID, sort);
        } else {
            // Retrieve all To-do by Label ID
            List<TodoLabel> todoLabelList = todoLabelService.retrieveTodoIdsByLabelId(label_id);
            List<String> todoIdList = new ArrayList<>();
            todoLabelList.forEach(
                    todoLabel -> todoIdList
                            .add(todoLabel.getTodoId())
            );
            todos = todoService.retrieveByTodoIdList(todoIdList, sort);
        }
        // Remove Completed tasks
        List<Todos> incompleteTasks = todos.stream()
                .filter(t -> !t.getIsCompleted())
                .collect(Collectors.toList());

        List<Labels> labelsList = labelService.retrieveAll(loginUserID);

        model.addAttribute("todos", incompleteTasks);
        model.addAttribute("labels", labelsList);
        model.addAttribute("todoForm", new TodoRequest());
        // Show Error message
        if(isError){
            model.addAttribute("errorMessage", "Try Again");
        }

        return INDEX_VIEW;
    }

    @GetMapping("/{todoId}/detail")
    public String showTaskDetail(@PathVariable("todoId") String todoId,
                                 Model model,
                                 @RequestParam(defaultValue = "false") Boolean isError){
        final String TODO_EDIT_VIEW = "todoDetail";
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
        List<TodoLabel> todoLabelList = todoLabelService.retrieveAllLabelId(todoId);
        List<String> labelIdList = new ArrayList<>();
        for (TodoLabel todoLabel: todoLabelList) {
            labelIdList.add(todoLabel.getLabelId());
        }
        List<Labels> relatedLabels = labelService.retrieveByLabelIds(labelIdList);
        model.addAttribute("request", TodoRequest.generateTodoRequest(todo));
        model.addAttribute("relatedLabels", relatedLabels);
        model.addAttribute("todoLabelRequest", request);
        if(isError){
            model.addAttribute("hasError", "Try Again");
        }
        return TODO_EDIT_VIEW;
    }

    @GetMapping("/delete/{todoId}")
    public String deleteTodo(@PathVariable("todoId") String todoId){
        todoService.delete(todoId);
        return "redirect:/to-do/list";
    }

    @PostMapping("/register")
    public String registerTodo (Principal principal,
                                @Valid @ModelAttribute(name = "todoForm") TodoRequest todoForm,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String queryParameter = "?isError=true";
            return "redirect:/to-do/list" + queryParameter;
        }
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        String loginUserId = principal.getName();
        // Store new object
        todoService.store(todoForm.getTitle(), currentDatetime, loginUserId);
        return "redirect:/to-do/list";
    }

    @PostMapping("/update/{todoId}")
    public String updateTodo(@PathVariable("todoId") String todoId,
                             @Valid @ModelAttribute TodoRequest request,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/to-do/" + todoId + "/detail"+ "?isError=true";
        }
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        // Update
        todoService.update(todoId, request, currentDatetime);
        return "redirect:/to-do/list";
    }

    @PostMapping("/complete/{todoId}")
    public String completeTodo(@PathVariable("todoId") String todoId){
        todoService.complete(todoId);
        return "redirect:/to-do/list";
    }
}
