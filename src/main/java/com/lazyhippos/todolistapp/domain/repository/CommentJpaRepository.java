package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comments, String> {
    List<Comments> findByArticleIdOrderByUpdatedDateTimeAsc(String articleId);

    Comments findByCommentId(String commentId);

    @Transactional
    @Modifying
    @Query("UPDATE Comments a SET a.textBody = :textBody,"
            + "a.updatedDateTime = :updatedDateTime"
            + " WHERE a.commentId = :commentId")
    void updateComment(@Param(value = "commentId") String commentId,
                       @Param(value = "textBody") String textBody,
                       @Param(value = "updatedDateTime") LocalDateTime updatedDateTime);
}
