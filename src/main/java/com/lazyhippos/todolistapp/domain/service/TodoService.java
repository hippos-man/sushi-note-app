package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoJpaRepository todoJpaRepository;


    public List<Todos> retrieve(String userId){

        return todoJpaRepository.findByUserId(userId);
    }

    public void store(Todos todoEntity){
        todoJpaRepository.save(todoEntity);
    }
}
