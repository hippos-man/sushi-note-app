package com.lazyhippos.todolistapp.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Articles {

    @Id
    private String articleId;
    private String userId;
    private String topicId;
    private String title;
    private String textBody;
    private Boolean isDeleted;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Articles() {
    }

    public Articles(String articleId, String userId, String topicId, String title,
                    String textBody, Boolean isDeleted, LocalDateTime createdDateTime,
                    LocalDateTime updatedDateTime) {
        this.articleId = articleId;
        this.userId = userId;
        this.topicId = topicId;
        this.title = title;
        this.textBody = textBody;
        this.isDeleted = isDeleted;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String article_id) {
        this.articleId = article_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topic_id) {
        this.topicId = topic_id;
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

    public void setTextBody(String text_body) {
        this.textBody = text_body;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime created_date_time) {
        this.createdDateTime = created_date_time;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updated_date_time) {
        this.updatedDateTime = updated_date_time;
    }
}
