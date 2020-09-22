package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    @Autowired
    private TodoJpaRepository todoJpaRepository;


    public List<Todos> retrieve(String userId){

        return todoJpaRepository.findByUserId(userId);
    }

    public void store(String todoTitle, LocalDateTime currentDateTime, String userId){
        // Generate UUID
        String todoId = UUID.randomUUID().toString();

        Todos todosEntity = new Todos(
                todoId,
                todoTitle,
                null,
                null,
                false,
                currentDateTime.toString(),
                null,
                null,
                userId
        );
        todoJpaRepository.save(todosEntity);
    }
}
