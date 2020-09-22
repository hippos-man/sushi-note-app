package com.lazyhippos.todolistapp.application.resource;

import java.io.Serializable;

public class TodoRequest implements Serializable {

    private String title;

    public TodoRequest(){}

    public TodoRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
