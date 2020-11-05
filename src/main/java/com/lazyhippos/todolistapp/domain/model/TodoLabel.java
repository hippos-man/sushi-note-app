package com.lazyhippos.todolistapp.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@IdClass(TodoLabelId.class)
public class TodoLabel{

    @Id
    private String todoId;
    @Id
    private String labelId;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public TodoLabel() {
    }

    public TodoLabel(String todoId, String labelId, LocalDateTime createdDateTime,
                     LocalDateTime updatedDateTime) {
        this.todoId = todoId;
        this.labelId = labelId;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
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
