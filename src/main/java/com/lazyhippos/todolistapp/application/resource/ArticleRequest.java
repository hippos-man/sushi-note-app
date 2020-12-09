package com.lazyhippos.todolistapp.application.resource;

public class ArticleRequest {

    private String articleId;
    private String userId;
    private String topicId;
    private String title;
    private String textBody;

    public ArticleRequest() {}

    public ArticleRequest(String articleId, String userId, String topicId, String title, String textBody) {
        this.articleId = articleId;
        this.userId = userId;
        this.topicId = topicId;
        this.title = title;
        this.textBody = textBody;
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

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "articleId='" + articleId + '\'' +
                "userId='" + userId + '\'' +
                ", topicId='" + topicId + '\'' +
                ", title='" + title + '\'' +
                ", textBody='" + textBody + '\'' +
                '}';
    }
}
