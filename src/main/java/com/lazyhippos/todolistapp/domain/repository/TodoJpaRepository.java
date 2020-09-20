package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.dto.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todos, String> {
    List<Todos> findByUserId(String userId);
}
