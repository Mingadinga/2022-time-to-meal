package com.team20.t4.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
    Optional<Member> findByMemberId(String memberId);
}
