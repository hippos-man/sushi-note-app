package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserJpaRepository extends JpaRepository<Users, String> {
    List<Users> findAllByUserIdIn(List<String> userIds);
}
