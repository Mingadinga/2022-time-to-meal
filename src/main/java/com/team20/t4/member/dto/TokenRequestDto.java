package com.team20.t4.member.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {
    @NotEmpty(message = "인증 토큰은 빈값일 수 없습니다.") String accessToken;
    @NotEmpty(message = "리프레시 토큰은 빈값일 수 없습니다.") String refreshToken;

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
