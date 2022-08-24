package com.team20.t4.common.exception;

import com.team20.t4.common.responseFormat.ErrorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RequestExceptionAdvice {
    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity requestException(RequestException e) {
        log.error("Request Exception({}) - {}", e.getErrorCode(), e.getErrorMessage());
        return new ErrorEntity(e.getErrorCode().toString(), e.getErrorMessage());
    }
}
