package com.lazyhippos.todolistapp.application.controller;

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

//    private final LabelService labelService;
//    private final TodoLabelService todoLabelService;
//    private final String LABEL_REGISTER_VIEW = "labelRegister";
//    private final String REDIRECT_TO_INDEX = "redirect:/to-do/list";
//
//    LabelController(LabelService labelService, TodoLabelService todoLabelService){
//        this.labelService = labelService;
//        this.todoLabelService = todoLabelService;
//    }
//
//    @GetMapping("/new")
//    public String showLabelRegister(Model model, @RequestParam(required = false) String todoId){
//        model.addAttribute("request", new LabelRequest(null, todoId, null));
//        return LABEL_REGISTER_VIEW;
//    }
//
//    @Transactional
//    @GetMapping("/remove")
//    public String remove(@RequestParam String todoId, @RequestParam String labelId){
//        todoLabelService.delete(todoId, labelId);
//        return BuildRedirectPathToTodoEditPage(todoId);
//    }
//    @Transactional
//    @GetMapping("/delete")
//    public String delete(@RequestParam String labelId){
//        // Delete the connection between to-do & label
//        todoLabelService.deleteAllByLabelId(labelId);
//        // Delete the label
//        labelService.delete(labelId);
//        return REDIRECT_TO_INDEX;
//    }
//
//    @PostMapping("/register")
//    public String register(@Valid @ModelAttribute(name = "request") LabelRequest request,
//                           Principal principal,
//                           BindingResult bindingResult,
//                           Model model){
//        if(bindingResult.hasErrors()){
//            model.addAttribute("request", request);
//            return LABEL_REGISTER_VIEW;
//        }
//        // Fetch current datetime
//        LocalDateTime currentDatetime = LocalDateTime.now();
//        String labelId = labelService.store(request, currentDatetime, principal.getName());
//        String view = REDIRECT_TO_INDEX;
//        if(request.getTodoId() != null && !request.getTodoId().isEmpty()){
//            todoLabelService.store(request.getTodoId(), labelId, currentDatetime);
//            view = BuildRedirectPathToTodoEditPage(request.getTodoId());
//        }
//        return view;
//    }
//
//
//    @PostMapping("/add")
//    public String add(@Valid @ModelAttribute(name = "request") TodoLabelRequest request,
//                      BindingResult bindingResult){
//        String todoId = request.getTodoId();
//        String errorQuery = "?isError=true";
//
//        if(bindingResult.hasErrors()){
//            return BuildRedirectPathToTodoEditPage(todoId) + errorQuery;
//        }
//
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//        if(request.getLabelsList().isEmpty()){
//            return BuildRedirectPathToTodoEditPage(todoId) + errorQuery;
//        }
//
//        String labelId = request.getLabelsList().get(0).getLabelId();
//        // Store
//        todoLabelService.store(todoId, labelId, currentDateTime);
//        return BuildRedirectPathToTodoEditPage(todoId);
//    }
//
//    static String BuildRedirectPathToTodoEditPage(String todoId) {
//        return "redirect:/to-do/" + todoId +"/edit";
//    }
}
