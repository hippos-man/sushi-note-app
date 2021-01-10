package com.lazyhippos.sushinote.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@IdClass(LikesId.class)
public class Likes {
    @Id
    private String userId;
    @Id
    private String articleId;
    private LocalDateTime createdDateTime;

    public Likes() {
    }

    public Likes(String userId, String articleId, LocalDateTime createdDateTime) {
        this.userId = userId;
        this.articleId = articleId;
        this.createdDateTime = createdDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
