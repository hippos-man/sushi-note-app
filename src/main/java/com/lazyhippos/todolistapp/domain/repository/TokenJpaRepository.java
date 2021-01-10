package com.lazyhippos.todolistapp.domain.repository;

import com.lazyhippos.todolistapp.domain.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJpaRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
