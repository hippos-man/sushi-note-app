package com.lazyhippos.sushinote.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    private byte[] content;
    private String originalName;
    private String filePath;
    private Long fileSize;
    @NotBlank
    private String userId;
    private LocalDateTime createdDateTime;

    public Documents() {
    }

    public Documents(byte[] content, String originalName, String filePath, Long fileSize, String userId,
                     LocalDateTime createdDateTime) {
        this.content = content;
        this.originalName = originalName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.userId = userId;
        this.createdDateTime = createdDateTime;
    }

    public Documents(Long documentId, String originalName, Long fileSize) {
        this.documentId = documentId;
        this.originalName = originalName;
        this.fileSize = fileSize;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
