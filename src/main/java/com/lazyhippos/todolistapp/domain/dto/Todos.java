package com.lazyhippos.todolistapp.domain.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Todos {

    @Id
    private String todoId;
    private String title;
    private String description;
    private String deadlineDate;
    private boolean isCompleted;
    private String createdDateTime;
    private String updatedDateTime;
    private String labelId;
    private String userId;

    public Todos() {
    }

    public Todos(String todoId, String title, String description, String deadlineDate,
                 boolean isCompleted, String createdDateTime, String updatedDateTime,
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

    public String deadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
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