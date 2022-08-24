package com.team20.t4.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        AuthErrorCode exception = (AuthErrorCode) request.getAttribute("exception");
        if(exception == null) {
            response.sendRedirect("/exception/entryPoint");
        }
        else if(exception.equals(AuthErrorCode.EXPIRED_TOKEN)) {
            response.sendRedirect("/exception/expiredToken");
        }
        else if(exception.equals(AuthErrorCode.MALFORMED_TOKEN)) {
            response.sendRedirect("/exception/malformedToken");
        }
        else if(exception.equals(AuthErrorCode.UNSUPPORTED_TOKEN)) {
            response.sendRedirect("/exception/unsupportedToken");
        }
        else if(exception.equals(AuthErrorCode.WRONG_TOKEN)) {
            response.sendRedirect("/exception/wrongToken");
        }
        else {
            response.sendRedirect("/exception/entryPoint");
        }
    }
}