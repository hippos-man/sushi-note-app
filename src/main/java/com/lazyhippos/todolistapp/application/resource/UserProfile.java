package com.lazyhippos.todolistapp.application.resource;

public class UserProfile {

    private String userId;
    private String displayName;
    private Boolean isActive;

    public UserProfile(String userId, String displayName, Boolean isActive) {
        this.userId = userId;
        this.displayName = displayName;
        this.isActive = isActive;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getActive() {
        return isActive;
    }
}
