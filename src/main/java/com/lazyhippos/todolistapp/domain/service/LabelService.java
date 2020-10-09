package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.LabelRequest;
import com.lazyhippos.todolistapp.domain.model.Labels;
import com.lazyhippos.todolistapp.domain.repository.LabelJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LabelService {

    private final LabelJpaRepository labelJpaRepository;

    LabelService(LabelJpaRepository labelJpaRepository){
        this.labelJpaRepository = labelJpaRepository;
    }


    public List<Labels> retrieveAll(String userId){
        List<Labels> labelsList = labelJpaRepository.findByUserId(userId);
        return labelsList;
    }

    public List<Labels> retrieveByLabelIds(List<String> labelIds){
        return labelJpaRepository.findByLabelIdIn(labelIds);
    }

    public void store(LabelRequest request, LocalDateTime currentDatetime, String userId){
        // Generate UUID
        String labelId = UUID.randomUUID().toString();
        Labels entity = new Labels(
                labelId,
                request.getLabelName(),
                request.getDescription(),
                currentDatetime,
                currentDatetime,
                userId,
                false
        );
        labelJpaRepository.save(entity);
    }
}
