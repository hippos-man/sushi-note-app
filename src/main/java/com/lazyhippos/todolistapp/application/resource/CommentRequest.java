package com.lazyhippos.todolistapp.application.resource;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentRequest {

    @NotBlank
    private String authorId;
    @NotBlank
    private String articleId;
    @NotBlank
    private String userId;
    @NotBlank
    private String textBody;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public CommentRequest() {
    }

    public CommentRequest (String authorId, String articleId, String userId, String textBody,
                          LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.authorId = authorId;
        this.articleId = articleId;
        this.userId = userId;
        this.textBody = textBody;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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
