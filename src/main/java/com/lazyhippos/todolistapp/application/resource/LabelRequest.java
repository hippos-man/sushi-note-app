package com.lazyhippos.todolistapp.application.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class LabelRequest implements Serializable {
    @NotBlank
    @Size(max = 50)
    private String labelName;
    @NotBlank
    private String todoId;
    @Size(max = 1500)
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
