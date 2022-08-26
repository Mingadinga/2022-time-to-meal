package com.team20.t4.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileImgRepository extends JpaRepository<MemberProfileImg, Long> {
    Optional<MemberProfileImg> findByMember(Member member);
}
