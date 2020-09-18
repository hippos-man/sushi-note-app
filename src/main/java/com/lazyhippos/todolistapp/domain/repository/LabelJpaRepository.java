package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.dto.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelJpaRepository extends JpaRepository<Labels, String> {
}
