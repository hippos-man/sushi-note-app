package com.lazyhippos.todolistapp.application.resource;

public class UserProfile {

    private String userId;
    private String displayName;
    private Long imageId;
    private Boolean isActive;

    public UserProfile() {
    }

    public UserProfile(String userId, String displayName, Long imageId, Boolean isActive) {
        this.userId = userId;
        this.displayName = displayName;
        this.imageId = imageId;
        this.isActive = isActive;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getImageId() {
        return imageId;
    }

    public Boolean getActive() {
        return isActive;
    }
}
