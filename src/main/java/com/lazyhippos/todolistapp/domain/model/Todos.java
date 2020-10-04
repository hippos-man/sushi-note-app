package com.lazyhippos.todolistapp.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Todos {

    @Id
    private String todoId;
    private String title;
    private String description;
    private LocalDateTime deadlineDate;
    private boolean isCompleted;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String labelId;
    private String userId;

    public Todos() {
    }

    public Todos(String todoId, String title, String description, LocalDateTime deadlineDate,
                 boolean isCompleted, LocalDateTime createdDateTime, LocalDateTime updatedDateTime,
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

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}