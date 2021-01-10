package com.lazyhippos.sushinote.domain.service;

import com.lazyhippos.sushinote.domain.model.Documents;
import com.lazyhippos.sushinote.domain.repository.DocumentJpaRepository;
import com.lazyhippos.sushinote.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    public final DocumentJpaRepository documentJpaRepository;

    DocumentService (DocumentJpaRepository documentJpaRepository) {
        this.documentJpaRepository = documentJpaRepository;
    }

    public Boolean save(Documents documents) {
        try {
            documentJpaRepository.save(documents);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Documents> retrieveAll () {
        return documentJpaRepository.findAll();
    }

    public Long getDocumentIdByFilePath(String filePath) {
        return documentJpaRepository.getDocumentIdByFilePath(filePath);
    }

    public Optional<Documents> retrieveById(Long documentId) {
        Optional<Documents> retrievedItem = documentJpaRepository.findById(documentId);
        if (!retrievedItem.isPresent()) {
            throw new EntityNotFoundException(Documents.class, "documentId", documentId.toString());
        }
        return retrievedItem;
    }
}
