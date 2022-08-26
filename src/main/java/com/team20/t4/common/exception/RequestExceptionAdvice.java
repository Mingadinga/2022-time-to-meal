package com.team20.t4.common.exception;

import com.team20.t4.common.responseFormat.ErrorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

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

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity emptyFileException(MultipartException e) {
        String errorName = e.getClass().getSimpleName();
        String errorMsg = e.getMessage();
        log.error("MultiFile Exception({}) - {}", errorName, errorMsg);
        return new ErrorEntity(errorName, errorMsg);
    }
}
