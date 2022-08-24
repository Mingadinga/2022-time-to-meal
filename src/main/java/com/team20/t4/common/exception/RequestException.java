package com.team20.t4.common.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {
    private RequestErrorCode errorCode;
    private String errorMessage;

    public RequestException(RequestErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultErrorMessage();
    }
    public RequestException(RequestErrorCode errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
