package com.lazyhippos.todolistapp.application.resource;

import javax.validation.constraints.*;
import java.io.Serializable;

public class UserRequest implements Serializable {
    @NotBlank
    @Size(max = 50)
    private String userId;
    @NotNull
    @Size(max = 50)
    private String firstName;
    @NotBlank
    @Size(max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    public UserRequest() {
    }

    public UserRequest(String userId, String firstName, String lastName,
                       String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    /**
     * Warn: Don't delete Setter as it's required for Binding Form Values
     * **/
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
