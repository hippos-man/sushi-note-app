package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.domain.model.Labels;
import com.lazyhippos.todolistapp.domain.repository.LabelJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

//@Controller
@RestController
public class LabelController {

    private final LabelJpaRepository labelJpaRepository;

    LabelController(LabelJpaRepository jpaRepository){
        this.labelJpaRepository = jpaRepository;
    }

//    @GetMapping("/label/all")
//    public List<Labels> getLabels(Principal principal){
//        // Retrieve Labels by Login user's ID
//        return labelJpaRepository.findByUserId(principal.getName());
//    }

//    // TODO Label Register page
//    public String showLabelDetail(){
//        return "labelDetail";
//    }
}
