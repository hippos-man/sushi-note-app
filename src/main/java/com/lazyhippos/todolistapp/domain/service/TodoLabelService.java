package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.TodoLabel;
import com.lazyhippos.todolistapp.domain.model.TodoLabelId;
import com.lazyhippos.todolistapp.domain.repository.TodoLabelJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoLabelService {

    private final TodoLabelJpaRepository todoLabelJpaRepository;

    TodoLabelService(TodoLabelJpaRepository repository){
        this.todoLabelJpaRepository = repository;
    }

    public List<String> retrieveLabelIds(String todoId){
        List<TodoLabel> todoLabelList = retrieveAllByTodoId(todoId);
        List<String> labelIdList = new ArrayList<>();
        todoLabelList.forEach(
                todoLabel -> labelIdList.add(todoLabel.getLabelId())
        );
        return labelIdList;
    }

    public List<TodoLabel> retrieveAllByTodoId(String todoId) {
        return todoLabelJpaRepository.findByTodoId(todoId);
    }

    public List<TodoLabel> retrieveAllByLabelId(String labelId){
        return todoLabelJpaRepository.findAllByLabelId(labelId);
    }

    public void store(String todoId, String labelId, LocalDateTime now){
        TodoLabel entity = new TodoLabel(
                todoId,
                labelId,
                now,
                now
        );
        todoLabelJpaRepository.save(entity);
    }

    public void delete(String todoId, String labelId){
        todoLabelJpaRepository.deleteByTodoIdAndLabelId(todoId, labelId);
    }
    public void deleteAllByLabelId(String labelId){
        todoLabelJpaRepository.deleteAllByLabelId(labelId);
    }
}
