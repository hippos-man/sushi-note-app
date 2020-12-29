package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Likes;
import com.lazyhippos.todolistapp.domain.model.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeJpaRepository extends JpaRepository<Likes, LikesId> {
    List<Likes> findAllByArticleIdIn(List<String> articleIds);
    Long countByArticleId(String articleId);
}
