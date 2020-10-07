package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/label")
@Controller
public class LabelController {

    @PostMapping("/register")
    public String register(){
        return null;
    }

    @GetMapping("/new")
    public String showLabelRegister(Model model){
        model.addAttribute("request", new LabelRequest());
        return "labelRegister";
    }
}
