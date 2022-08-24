package com.team20.t4.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken){
        this.grantType = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
