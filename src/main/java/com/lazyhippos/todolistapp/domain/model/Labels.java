package com.lazyhippos.todolistapp.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Labels {

    @Id
    private String labelId;
    private String labelName;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String userId;
    private Boolean isDeleted;

    public Labels() {
    }

    public Labels(String labelId, String labelName, String description,
                  LocalDateTime createdDateTime, LocalDateTime updatedDateTime,
                  String userId, Boolean isDeleted) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
        this.isDeleted = isDeleted;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}