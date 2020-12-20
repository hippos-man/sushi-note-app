package com.lazyhippos.todolistapp.domain.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Validated
public class Articles {

    @Id
    @NotBlank
    private String articleId;
    @NotBlank
    private String userId;
    @NotBlank
    @Size(max = 200)
    private String topicId;
    @Size(max = 200)
    @NotBlank
    private String title;
    @NotBlank
    private String textBody;
    @NotBlank
    private String summary;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Articles() {
    }

    public Articles(String articleId, String userId, String topicId, String title, String textBody,
                    String summary, LocalDateTime createdDateTime,
                    LocalDateTime updatedDateTime) {
        this.articleId = articleId;
        this.userId = userId;
        this.topicId = topicId;
        this.title = title;
        this.textBody = textBody;
        this.summary = summary;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
