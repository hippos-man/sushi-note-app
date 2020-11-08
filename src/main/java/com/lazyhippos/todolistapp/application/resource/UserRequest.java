package com.lazyhippos.todolistapp.application.resource;

import javax.validation.constraints.*;
import java.io.Serializable;

public class UserRequest implements Serializable {
    @NotBlank
    @Size(max = 50)
    private String userId;

    @NotBlank
    @Size(max = 50)
    private String displayName;

    @NotBlank
    private String emailAddress;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    public UserRequest() {
    }

    public UserRequest(@NotBlank @Size(max = 50) String userId,
                       @NotBlank @Size(max = 50) String displayName,
                       @NotBlank String emailAddress,
                       @NotBlank @Size(min = 8, max = 50) String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
