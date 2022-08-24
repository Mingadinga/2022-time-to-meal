package com.team20.t4.member.domain;

import com.team20.t4.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberPk;

    @Column(name = "refresh_token_value", nullable = false)
    private String refreshTokenValue;

    public RefreshToken updateRefreshTokenValue(String refreshTokenValue) {
        this.refreshTokenValue = refreshTokenValue;
        return this;
    }

    @Builder
    public RefreshToken(Long key, String refreshTokenValue) {
        this.memberPk = key;
        this.refreshTokenValue = refreshTokenValue;
    }
}