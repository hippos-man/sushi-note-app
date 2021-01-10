package com.lazyhippos.sushinote.domain.repository;

import com.lazyhippos.sushinote.domain.model.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicJpaRepository extends JpaRepository<Topics, String> {
}
