package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.repository.CommentJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentJpaRepository commentJpaRepository;

    CommentService (CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    public List<Comments> retrieveAll() {
        return commentJpaRepository.findAll();
    }

    public List<Comments> retrieveByArticleId(String articleId) {
        return commentJpaRepository.findByArticleId(articleId);
    }
}
