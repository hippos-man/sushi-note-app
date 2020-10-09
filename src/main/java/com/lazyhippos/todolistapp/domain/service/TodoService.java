package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.TodoRequest;
import com.lazyhippos.todolistapp.domain.model.Todos;
import com.lazyhippos.todolistapp.domain.repository.TodoJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoJpaRepository todoJpaRepository;

    TodoService(TodoJpaRepository todoJpaRepository){
        this.todoJpaRepository = todoJpaRepository;
    }

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
                currentDateTime,
                currentDateTime,
                userId
        );
        todoJpaRepository.save(todosEntity);
    }

    public void update (String todoId, TodoRequest request, LocalDateTime now){
        // Fetch the entity
        Todos task = todoJpaRepository.getOne(todoId);
        // Update
        if(request.getTitle() != null && !request.getTitle().isEmpty()){
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null){
            task.setDescription(request.getDescription());
        }
        if (request.getDeadlineDate() != null){
            task.setDeadlineDate(request.getDeadlineDate());
        }

        task.setUpdatedDateTime(now);

        // Execute
        todoJpaRepository.save(task);
    }

    public void complete(String todoId){
        // Fetch entity which user complete
        Todos todo = todoJpaRepository.getOne(todoId);
        // Update isCompleted
        todo.setIsCompleted(true);
        // Execute
        todoJpaRepository.save(todo);
    }
}
