package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.ArticleRequest;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.repository.ArticleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleJpaRepository articleJpaRepository;

    public ArticleService(ArticleJpaRepository articleJpaRepository){
        this.articleJpaRepository = articleJpaRepository;
    }

    public List<Articles> retrieveAll() {
        return articleJpaRepository.findAll();
    }

    public List<Articles> retrieveByTopicId(String topicId) {
        return articleJpaRepository.findByTopicId(topicId);
    }

    public Optional<Articles> retrieveByArticleId(String articleId) {
        return articleJpaRepository.findById(articleId);
    }

    public void save(ArticleRequest request, LocalDateTime now) {
        // Generate UUID
        String articleId = UUID.randomUUID().toString();
        articleJpaRepository.save(new Articles(
                articleId,
                request.getUserId(),
                request.getTopicId(),
                request.getTitle(),
                request.getTextBody(),
                false,
                now,
                now
        ));
    }

    public void update(ArticleRequest request, LocalDateTime now) {
        // TODO
    }
}
