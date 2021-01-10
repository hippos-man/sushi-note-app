package com.lazyhippos.sushinote.domain.repository;

import com.lazyhippos.sushinote.domain.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJpaRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
