package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentJpaRepository extends JpaRepository<Documents, Long> {
    @Query("SELECT new Documents(d.documentId, d.originalName, d.fileSize) FROM Documents d ORDER BY d.createdDateTime DESC")
    List<Documents> findAll();

    @Query("SELECT d.documentId FROM Documents d WHERE d.originalName = :fileName")
    Long getDocumentIdByOriginalName(@Param(value = "fileName") String fileName);
}
