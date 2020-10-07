package com.lazyhippos.todolistapp.application.resource;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LabelRequest implements Serializable {

    private String labelId;
    private String labelName;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String userId;
    private Boolean isDeleted;

    public LabelRequest() {
    }

    public LabelRequest(String labelId, String labelName, String description, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, String userId, Boolean isDeleted) {

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

    public String getLabelName() {
        return labelName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }
}
