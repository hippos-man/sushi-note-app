package com.lazyhippos.todolistapp.application.resource;

import java.time.LocalDateTime;

public class CommentResponse {

    private String commentId;
    private String articleId;
    private String userId;
    private String textBody;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public CommentResponse() {
    }

    public CommentResponse(String commentId, String articleId, String userId, String textBody,
                           LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userId = userId;
        this.textBody = convertToHtml(textBody);
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTextBody() {
        return textBody;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public static String convertToHtml(String rawData) {

        if (rawData == null) {
            throw new RuntimeException();
        }

        final String P_OPEN_TAG = "<p>";
        final String P_CLOSE_TAG = "</p>";
        final String LINE_BREAK = "<br />";

        StringBuilder stringBuilder = new StringBuilder();
        String[] paragraph = rawData.split("\\r\\n\\r\\n");
        for (int i = 0; i < paragraph.length; i++) {
            stringBuilder.append(P_OPEN_TAG);
            stringBuilder.append(paragraph[i]);
            stringBuilder.append(P_CLOSE_TAG);
        }
        String taggedText = stringBuilder.toString();
        taggedText = taggedText.replace("\r\n", LINE_BREAK);
        return taggedText;
    }
}
