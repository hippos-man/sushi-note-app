package com.lazyhippos.todolistapp.application.resource;

public class CommentUpdateRequest {

    private String commentId;
    private String articleId;
    private String authorId;
    private String textBody;

    public CommentUpdateRequest() {
    }

    public CommentUpdateRequest(String commentId, String articleId, String authorId, String textBody) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.authorId = authorId;
        this.textBody = textBody;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }
}
