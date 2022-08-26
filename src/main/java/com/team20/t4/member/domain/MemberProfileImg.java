package com.team20.t4.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_profile_img")
@Getter
@NoArgsConstructor
public class MemberProfileImg {
    @Id
    @Column(name = "member_profile_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "memberPk")
    private Member member;

    @Column(name = "img_key", nullable = false)
    private String fileKey;

    public MemberProfileImg updateFileKey(String fileKey) {
        this.fileKey = fileKey;
        return this;
    }

    @Builder
    public MemberProfileImg(Member member, String fileKey) {
        this.member = member;
        this.fileKey = fileKey;
    }
}
