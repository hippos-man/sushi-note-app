package com.lazyhippos.todolistapp.application.resource;

import java.time.LocalDateTime;

public class TodoRequest {
    private String todoId;
    private String title;
    private String description;
    private LocalDateTime deadlineDate;
    private Boolean isCompleted;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String labelId;
    private String userId;

    public TodoRequest(String todoId, String title, String description, LocalDateTime deadlineDate,
                       Boolean isCompleted, LocalDateTime createdDateTime, LocalDateTime updatedDateTime,
                       String labelId, String userId) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.isCompleted = isCompleted;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.labelId = labelId;
        this.userId = userId;
    }

    public String getTodoId() {
        return todoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public String getLabelId() {
        return labelId;
    }

    public String getUserId() {
        return userId;
    }


}
