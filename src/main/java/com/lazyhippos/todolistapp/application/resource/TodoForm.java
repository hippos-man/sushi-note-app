package com.lazyhippos.todolistapp.application.resource;

import java.io.Serializable;

public class TodoForm implements Serializable {

    private String title;

    public TodoForm(){}

    public TodoForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
