package com.team20.t4.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static Optional<Long> getCurrentUserPk() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        Long userPk = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userPk = Long.parseLong(springSecurityUser.getUsername());

        } else if (authentication.getPrincipal() instanceof String) {
            userPk = (Long) authentication.getPrincipal();
        }

        return Optional.ofNullable(userPk);
    }
}
