package com.lazyhippos.sushinote.application.resource;


public class UserUpdateRequest {

    private String userId;
    private String displayName;
    private String emailAddress;
    private Long imageId;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String userId, String displayName, String emailAddress, Long imageId) {
        this.userId = userId;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.imageId = imageId;
    }

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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
