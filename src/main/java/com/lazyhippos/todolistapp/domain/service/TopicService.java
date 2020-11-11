package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Topics;
import com.lazyhippos.todolistapp.domain.repository.TopicJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicJpaRepository topicJpaRepository;

    TopicService(TopicJpaRepository topicJpaRepository) {
        this.topicJpaRepository = topicJpaRepository;
    }

    public List<Topics> retrieveAll(){
        return topicJpaRepository.findAll();
    }
}
