package com.lazyhippos.todolistapp.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class LikesId implements Serializable {
    private String userId;
    private String articleId;

    public LikesId() {
    }

    public LikesId(String userId, String articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikesId likesId = (LikesId) o;
        return Objects.equals(userId, likesId.userId) &&
                Objects.equals(articleId, likesId.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, articleId);
    }
}
