package com.lazyhippos.todolistapp.application.resource;

import java.time.LocalDateTime;

public class ArticleSummary {

    private String articleId;
    private String userId;
    private String topicId;
    private String title;
    private String summary;
    private Long imageId;
    private Long profileImageId;
    private Long likeCount;
    private Long commentCount;
    private String updatedDateTime;

    public ArticleSummary() {
    }

    public ArticleSummary(String articleId, String userId, String topicId, String title, String summary,
                          Long imageId, Long profileImageId, Long likeCount, Long commentCount, LocalDateTime updatedDateTime) {
        this.articleId = articleId;
        this.userId = userId;
        this.topicId = topicId;
        this.title = title;
        this.summary = summary;
        this.imageId = imageId;
        this.profileImageId = profileImageId;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.updatedDateTime = updatedDateTime.getMonth().toString().substring(0, 1)
                + updatedDateTime.getMonth().toString().substring(1).toLowerCase()
                + " "
                + updatedDateTime.getDayOfMonth()
                + ", "
                + updatedDateTime.getYear();
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Long profileImageId) {
        this.profileImageId = profileImageId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
