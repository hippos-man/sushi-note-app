package com.lazyhippos.todolistapp.domain.model;

import java.io.Serializable;

public class TodoLabelId implements Serializable {

    private String todoId;
    private String labelId;

    public TodoLabelId() {
    }

    public TodoLabelId(String todoId, String labelId) {
        this.todoId = todoId;
        this.labelId = labelId;
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

}
