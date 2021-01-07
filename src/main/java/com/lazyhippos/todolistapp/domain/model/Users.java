package com.lazyhippos.todolistapp.domain.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Users {

    @Id
    private String userId;
    private String displayName;
    private String emailAddress;
    private String password;
    public Long imageId;
    private Boolean isActive;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
    private Boolean isEnabled;

    public Users() {
    }

    public Users(String userId, String displayName, String emailAddress, String password, Long imageId,
                 Boolean isActive, LocalDateTime createdDateTime, LocalDateTime updatedDateTime,
                 RoleName roleName) {
        this.userId = userId;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.imageId = imageId;
        this.isActive = isActive;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.roleName = roleName;
        this.isEnabled = false;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}