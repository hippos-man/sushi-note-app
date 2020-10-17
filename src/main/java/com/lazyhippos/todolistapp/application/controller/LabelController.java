package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import com.lazyhippos.todolistapp.application.resource.TodoLabelRequest;
import com.lazyhippos.todolistapp.domain.service.LabelService;
import com.lazyhippos.todolistapp.domain.service.TodoLabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RequestMapping("/label")
@Controller
public class LabelController {

    private final LabelService labelService;
    private final TodoLabelService todoLabelService;

    LabelController(LabelService labelService, TodoLabelService todoLabelService){
        this.labelService = labelService;
        this.todoLabelService = todoLabelService;
    }

    @PostMapping("/register")
    public String register(Principal principal, @ModelAttribute LabelRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        String labelId = labelService.store(request, currentDatetime, principal.getName());
        System.out.println("New Label ID : " + labelId);
        String view = "redirect:/to-do/list";
        if(request.getTodoId() != null && !request.getTodoId().isEmpty()){
            System.out.println("Assign to the to-do. TODO ID : " + request.getTodoId());
            todoLabelService.store(request.getTodoId(), labelId, currentDatetime);
            view = "redirect:/to-do/" + request.getTodoId() + "/detail";
        }
        return view;
    }



    @PostMapping("/add")
    public String add(@ModelAttribute TodoLabelRequest request){
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Retrieve TodoID
        String todoId = request.getTodoId();
        // TODO Array Size check
        String labelId = request.getLabelsList().get(0).getLabelId();
        // Store
        todoLabelService.store(todoId, labelId, currentDateTime);
        String view = "redirect:/to-do/" + todoId + "/detail";
        return view;
    }

    @GetMapping("/new")
    public String showLabelRegister(Model model, @RequestParam(required = false) String todoId){
            model.addAttribute("request", new LabelRequest(null, todoId, null));
        return "labelRegister";
    }
}
