package com.lazyhippos.todolistapp.domain.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Comments {

    @Id
    private String commentId;
    private String articleId;
    private String userId;
    private String textBody;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Comments() {
    }

    public Comments(String commentId, String articleId, String userId, String textBody,
                    LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userId = userId;
        this.textBody = textBody;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
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
}
