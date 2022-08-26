package com.team20.t4.common.s3;

import lombok.Getter;

@Getter
public class S3FileException extends RuntimeException{
    private FileErrorCode errorCode;
    private String errorMessage;

    public S3FileException(FileErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultErrorMessage();
    }
    public S3FileException(FileErrorCode errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
