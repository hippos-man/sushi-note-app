package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.repository.ArticleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
