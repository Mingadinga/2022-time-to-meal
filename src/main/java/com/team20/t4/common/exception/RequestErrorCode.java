package com.team20.t4.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestErrorCode {
    DUPLICATED("기존 요청과 중복된 요청입니다."),
    NOT_FOUND("존재하지 않는 리소스를 요청했습니다."),

    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),
    EMPTY_FILE("파일이 없습니다. 파일을 첨부해주세요.");

    private String defaultErrorMessage;
}
