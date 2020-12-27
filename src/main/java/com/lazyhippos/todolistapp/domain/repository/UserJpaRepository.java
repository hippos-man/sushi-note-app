package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<Users, String> {
    List<Users> findAllByUserIdIn(List<String> userIds);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.displayName = :displayName,"
            + "u.emailAddress = :emailAddress,"
            + "u.imageId = :imageId,"
            + "u.updatedDateTime = :updatedDateTime"
            + " WHERE u.userId = :userId")
    void updateUserProfile(
            @Param(value = "userId") String userId,
            @Param(value = "displayName") String displayName,
            @Param(value = "emailAddress") String emailAddress,
            @Param(value = "imageId") Long imageId,
            @Param(value = "updatedDateTime") LocalDateTime updatedDateTime);
}
