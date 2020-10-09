package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import com.lazyhippos.todolistapp.application.resource.TodoLabelRequest;
import com.lazyhippos.todolistapp.domain.service.LabelService;
import com.lazyhippos.todolistapp.domain.service.TodoLabelService;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        labelService.store(request, currentDatetime, principal.getName());
        return "redirect:/to-do/all";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TodoLabelRequest request){
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Retrieve TodoID
        String todoId = request.getTodoId();
        String labelId = request.getLabelsList().get(0).getLabelId();
        // Store
        todoLabelService.store(todoId, labelId, currentDateTime);
        String view = "redirect:/to-do/" + todoId + "/detail";
        return view;
    }

    @GetMapping("/new")
    public String showLabelRegister(Model model){
        model.addAttribute("request", new LabelRequest());
        return "labelRegister";
    }
}
