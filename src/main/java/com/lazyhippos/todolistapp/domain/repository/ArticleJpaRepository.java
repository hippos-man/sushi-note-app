package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Articles, String> {
}
