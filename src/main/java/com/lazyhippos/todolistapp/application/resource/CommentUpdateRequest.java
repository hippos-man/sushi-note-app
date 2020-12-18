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

    public String getArticleId() {
        return articleId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTextBody() {
        return textBody;
    }
}
