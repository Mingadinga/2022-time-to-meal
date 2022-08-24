package com.team20.t4.security.exception;

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
public class AuthExceptionAdvice {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorEntity authException(AuthException e) {
        log.error("Auth Exception({}) - {}", e.getErrorCode().toString(), e.getErrorMessage());
        return new ErrorEntity(e.getErrorCode().toString(), e.getErrorMessage());
    }

}
