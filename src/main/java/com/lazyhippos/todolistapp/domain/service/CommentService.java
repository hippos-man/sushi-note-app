package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.CommentRequest;
import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.repository.CommentJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        return commentJpaRepository.findByArticleIdOrderByUpdatedDateTimeAsc(articleId);
    }

    public Comments retrieveByCommentId(String commentId) {
        return commentJpaRepository.findByCommentId(commentId);
    }

    public void save (CommentRequest request, LocalDateTime now) {
        // Generate UUID
        String commentId = UUID.randomUUID().toString();
        commentJpaRepository.save(
                new Comments(
                        commentId,
                        request.getArticleId(),
                        request.getUserId(),
                        request.getTextBody(),
                        now,
                        now
                )
        );
    }

    public void update (String commentId, String textBody, LocalDateTime now) {
        commentJpaRepository.updateComment(commentId, textBody, now);
    }

    public Boolean delete (String commentId) {
        commentJpaRepository.deleteById(commentId);
        return true;
    }
}
