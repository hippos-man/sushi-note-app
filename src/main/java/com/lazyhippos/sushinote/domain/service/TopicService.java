package com.lazyhippos.sushinote.domain.service;

import com.lazyhippos.sushinote.domain.model.Topics;
import com.lazyhippos.sushinote.domain.repository.TopicJpaRepository;
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
