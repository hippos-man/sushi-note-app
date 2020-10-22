package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import com.lazyhippos.todolistapp.application.resource.TodoLabelRequest;
import com.lazyhippos.todolistapp.domain.service.LabelService;
import com.lazyhippos.todolistapp.domain.service.TodoLabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@RequestMapping("/label")
@Controller
public class LabelController {

    private final LabelService labelService;
    private final TodoLabelService todoLabelService;
    private final String LABEL_REGISTER_VIEW = "labelRegister";

    LabelController(LabelService labelService, TodoLabelService todoLabelService){
        this.labelService = labelService;
        this.todoLabelService = todoLabelService;
    }

    @PostMapping("/register")
    public String register(Principal principal,
                           @Valid @ModelAttribute(name = "request") LabelRequest request,
                           BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("request", request);
            return LABEL_REGISTER_VIEW;
        }
        // Fetch current datetime
        LocalDateTime currentDatetime = LocalDateTime.now();
        String labelId = labelService.store(request, currentDatetime, principal.getName());
        String view = "redirect:/to-do/list";
        if(request.getTodoId() != null && !request.getTodoId().isEmpty()){
            todoLabelService.store(request.getTodoId(), labelId, currentDatetime);
            view = "redirect:/to-do/" + request.getTodoId() + "/detail";
        }
        return view;
    }



    @PostMapping("/add")
    public String add(@Valid @ModelAttribute(name = "request") TodoLabelRequest request,
                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/to-do/" + request.getTodoId() + "/detail"  + "?isError=true";
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Retrieve TodoID
        String todoId = request.getTodoId();
        // TODO Array Size check
        String labelId = request.getLabelsList().get(0).getLabelId();
        // Store
        todoLabelService.store(todoId, labelId, currentDateTime);
        return "redirect:/to-do/" + todoId + "/detail";
    }

    @Transactional
    @GetMapping("/remove")
    public String remove(@RequestParam String todoId, @RequestParam String labelId){
        todoLabelService.delete(todoId, labelId);
        return "redirect:/to-do/" + todoId + "/detail";
    }
    @Transactional
    @GetMapping("/delete")
    public String delete(@RequestParam String labelId){
        todoLabelService.deleteAllByLabelId(labelId);
        labelService.delete(labelId);
        return "redirect:/to-do/list";
    }

    @GetMapping("/new")
    public String showLabelRegister(Model model, @RequestParam(required = false) String todoId){
            model.addAttribute("request", new LabelRequest(null, todoId, null));
        return LABEL_REGISTER_VIEW;
    }
}
