package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.repository.TodoLabelJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoLabelService {

    private final TodoLabelJpaRepository todoLabelJpaRepository;

    TodoLabelService(TodoLabelJpaRepository repository){
        this.todoLabelJpaRepository = repository;
    }

    public List<String> retrieveALlLabelId(String todoId) {
        return todoLabelJpaRepository.findByTodoId(todoId);
    }
}
