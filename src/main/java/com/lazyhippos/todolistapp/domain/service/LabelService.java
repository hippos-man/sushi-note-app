package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Labels;
import com.lazyhippos.todolistapp.domain.repository.LabelJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelJpaRepository labelJpaRepository;

    LabelService(LabelJpaRepository labelJpaRepository){
        this.labelJpaRepository = labelJpaRepository;
    }


    public List<Labels> retrieveAll(String userId){
        List<Labels> labelsList = labelJpaRepository.findByUserId(userId);
        return labelsList;
    }
}
