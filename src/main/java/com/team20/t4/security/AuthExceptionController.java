package com.team20.t4.security;

import com.team20.t4.security.exception.AuthErrorCode;
import com.team20.t4.security.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class AuthExceptionController {

    @GetMapping("/entryPoint")
    public void entrypointException() { throw new AuthException(AuthErrorCode.AUTHENTICATION_ENTRYPOINT); }

    @GetMapping("/accessDenied")
    public void accessDeniedException() { throw new AuthException(AuthErrorCode.ACCESS_DENIED); }

    @GetMapping("/expiredToken")
    public void expiredTokenException(){ throw new AuthException(AuthErrorCode.EXPIRED_TOKEN); }

    @GetMapping("/malformedToken")
    public void malformedTokenException(){ throw new AuthException(AuthErrorCode.MALFORMED_TOKEN); }

    @GetMapping("/unsupportedToken")
    public void unsupportedTokenException(){ throw new AuthException(AuthErrorCode.UNSUPPORTED_TOKEN); }

    @GetMapping("/wrongToken")
    public void wrongTokenException(){ throw new AuthException(AuthErrorCode.WRONG_TOKEN); }

}
