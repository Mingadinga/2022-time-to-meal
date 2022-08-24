package com.team20.t4.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    EXPIRED_TOKEN("만료된 토큰입니다."),
    MALFORMED_TOKEN("잘못된 Jwt 서명입니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰입니다."),
    WRONG_TOKEN("잘못된 토큰입니다."),
    AUTHENTICATION_ENTRYPOINT("인증에 실패했습니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),
    EXPIRED_REFRESH_TOKEN("만료된 리프레시 토큰입니다."),
    UNSAVED_REFRESH_TOKEN("저장되지 않은 리프레시 토큰입니다."),
    INVALID_REFRESH_TOKEN("저장된 리프레시 토큰과 일치하지 않는 리프레시 토큰입니다.");

    private String defaultErrorMessage;
}
