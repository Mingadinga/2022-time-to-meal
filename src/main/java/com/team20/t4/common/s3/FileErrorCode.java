package com.team20.t4.common.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode {
    EMPTY_FILE("파일이 없습니다. 파일을 첨부해주세요."),
    FILE_UPLOAD_FAIL("파일 업로드에 실패했습니다."),
    FILE_DELETE_FAIL("파일 삭제에 실패했습니다."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다.");
    private String defaultErrorMessage;
}