package com.lazyhippos.sushinote.application.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommentResponse {

    private String commentId;
    private String articleId;
    private String commenterId;
    private String displayName;
    private String textBody;
    private Long imageId;
    private LocalDateTime createdDateTime;
    private String updatedDateTime;

    public CommentResponse() {
    }

    public CommentResponse(String commentId, String articleId, String commenterId, String displayName, String textBody,
                           Long imageId, LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.commenterId = commenterId;
        this.displayName = displayName;
        this.textBody = convertToHtml(textBody);
        this.imageId = imageId;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = isToday(updatedDateTime, LocalDateTime.now())
                ? "Today"
                    + " "
                    + updatedDateTime.getHour()
                    + ":"
                    + formatMinute(updatedDateTime.getMinute())
                : isThisYear(updatedDateTime, LocalDateTime.now())
                ?
                updatedDateTime.getMonth().toString().substring(0, 1)
                        + updatedDateTime.getMonth().toString().substring(1).toLowerCase()
                        + " "
                        + updatedDateTime.getDayOfMonth()
                :
                updatedDateTime.getMonth().toString().substring(0, 1)
                    + updatedDateTime.getMonth().toString().substring(1).toLowerCase()
                    + " "
                    + updatedDateTime.getDayOfMonth()
                    + ", "
                    + updatedDateTime.getYear();
    }

    public String getCommentId() {
        return commentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTextBody() {
        return textBody;
    }

    public Long getImageId() {
        return imageId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getUpdatedDateTime() {
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

    public static Boolean isToday(LocalDateTime input, LocalDateTime currentTime) {
        LocalDate today = currentTime.toLocalDate();
        LocalDate datetime = input.toLocalDate();
        return datetime.equals(today);
    }

    public static Boolean isThisYear (LocalDateTime input, LocalDateTime currentTime) {
        return input.getYear() == currentTime.getYear();
    }

    public static String formatMinute(int input) {
        if (input < 10) {
            return "0" + input;
        } else {
            return String.valueOf(input);
        }
    }

}
