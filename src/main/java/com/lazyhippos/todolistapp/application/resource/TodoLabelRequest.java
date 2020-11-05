package com.lazyhippos.todolistapp.application.resource;

import com.lazyhippos.todolistapp.domain.model.Labels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class TodoLabelRequest {
    @Size(min = 1 )
    private List<Labels> labelsList;
    @NotBlank
    private String todoId;

    public TodoLabelRequest() {
    }

    public TodoLabelRequest(List<Labels> labelsList, String todoId) {
        this.labelsList = labelsList;
        this.todoId = todoId;
    }

    public List<Labels> getLabelsList() {
        return labelsList;
    }

    public void setLabelsList(List<Labels> labelsList) {
        this.labelsList = labelsList;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }
}
