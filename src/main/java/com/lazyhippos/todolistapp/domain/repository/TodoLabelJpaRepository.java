package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.TodoLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoLabelJpaRepository extends JpaRepository<TodoLabel, String> {
    List<TodoLabel> findByTodoId(String todoId);
    List<TodoLabel> findAllByLabelId(String labelId);
}
