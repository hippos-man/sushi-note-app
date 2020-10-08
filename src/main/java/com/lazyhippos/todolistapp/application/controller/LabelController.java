package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import com.lazyhippos.todolistapp.domain.service.LabelService;
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

    LabelController(LabelService labelService){
        this.labelService = labelService;
    }

    @PostMapping("/register")
    public String register(Principal principal, @ModelAttribute LabelRequest request){
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        labelService.store(request, currentDatetime, principal.getName());
        return "redirect:/to-do/all";
    }

    @GetMapping("/new")
    public String showLabelRegister(Model model){
        model.addAttribute("request", new LabelRequest());
        return "labelRegister";
    }
}
