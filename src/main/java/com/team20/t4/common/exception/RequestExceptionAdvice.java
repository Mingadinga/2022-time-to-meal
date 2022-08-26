package com.team20.t4.common.exception;

import com.team20.t4.common.responseFormat.ErrorEntity;
import com.team20.t4.common.s3.S3FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorCode = e.getBindingResult().getAllErrors().get(0).getCode();
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValid Exception({}) - {}", errorCode, errorMessage);
        return new ErrorEntity(errorCode, errorMessage);
    }

    @ExceptionHandler({S3FileException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity s3FileException(S3FileException e) {
        log.error("S3 File Exception({}) - {}", e.getErrorCode(), e.getErrorMessage());
        return new ErrorEntity(e.getErrorCode().toString(), e.getErrorMessage());
    }
}
