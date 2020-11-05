package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<Users, String> {
}
