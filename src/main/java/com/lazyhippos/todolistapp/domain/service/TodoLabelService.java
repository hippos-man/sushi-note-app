package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.TodoLabel;
import com.lazyhippos.todolistapp.domain.repository.TodoLabelJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoLabelService {

    private final TodoLabelJpaRepository todoLabelJpaRepository;

    TodoLabelService(TodoLabelJpaRepository repository){
        this.todoLabelJpaRepository = repository;
    }

    public List<TodoLabel> retrieveALlLabelId(String todoId) {
        return todoLabelJpaRepository.findByTodoId(todoId);
    }

    public void store(String todoId, String labelId, LocalDateTime now){
        TodoLabel entity = new TodoLabel(
                todoId,
                labelId,
                now,
                now,
                false
        );
        todoLabelJpaRepository.save(entity);
    }
}
