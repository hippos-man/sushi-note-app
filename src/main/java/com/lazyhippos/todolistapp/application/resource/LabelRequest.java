package com.lazyhippos.todolistapp.application.resource;

import java.io.Serializable;

public class LabelRequest implements Serializable {

    private String labelName;
    private String todoId;
    private String description;

    public LabelRequest() {
    }

    public LabelRequest(String labelName, String todoId, String description) {
        this.labelName = labelName;
        this.todoId = todoId;
        this.description = description;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getDescription() {
        return description;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
