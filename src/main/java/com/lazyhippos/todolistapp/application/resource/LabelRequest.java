package com.lazyhippos.todolistapp.application.resource;

import java.io.Serializable;

public class LabelRequest implements Serializable {

    private String labelName;
    private String description;

    public LabelRequest() {
    }

    public LabelRequest(String labelName, String description) {
        this.labelName = labelName;
        this.description = description;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
