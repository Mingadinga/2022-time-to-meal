package com.team20.t4.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberPk(Long id);
    boolean existsByMemberPk(Long id);
}
