package com.lazyhippos.sushinote.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Topics {

    @Id
    private String topicId;
    private String userId;
    private String topicName;
    private String isActive;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Topics() {
    }

    public Topics(String topicId, String userId, String topicName, String isActive, LocalDateTime createdDateTime,
                  LocalDateTime updatedDateTime) {
        this.topicId = topicId;
        this.userId = userId;
        this.topicName = topicName;
        this.isActive = isActive;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
