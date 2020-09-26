package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
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


    public List<Todos> retrieveAll(String userId){
        return todoJpaRepository.findByUserId(userId);
    }

    public Todos retrieveOne(String todoId){
        return todoJpaRepository.getOne(todoId);
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

    public void update (String todoId, TodoRequest request){
        // Fetch the entity
        Todos task = todoJpaRepository.getOne(todoId);
        // Update
        if(request.getTitle() != null){
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null){
            task.setDescription(request.getDescription());
        }
        if (request.getDeadlineDate() != null){
            System.out.println("DeadlineDateTime is : " + task.getDeadlineDate());
            task.setDeadlineDate(request.getDeadlineDate().toString());
        }
        if (request.getLabelId() != null){
            task.setLabelId(request.getLabelId());
        }
        // Execute
        todoJpaRepository.save(task);
    }
}
