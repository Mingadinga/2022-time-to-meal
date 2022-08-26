package com.team20.t4.member;

import com.team20.t4.common.exception.RequestException;
import com.team20.t4.member.domain.Member;
import com.team20.t4.member.domain.RefreshToken;
import com.team20.t4.member.domain.RefreshTokenRepository;
import com.team20.t4.security.JwtProvider;
import com.team20.t4.security.exception.AuthErrorCode;
import com.team20.t4.security.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public String updateRefreshToken(Member member) throws RequestException {
        String refreshTokenValue = createRefreshTokenValue();
        String refreshToken = jwtProvider.createRefreshToken(refreshTokenValue);
        saveOrUpdateRefreshTokenValue(member, refreshTokenValue);
        return refreshToken;
    }

    private String createRefreshTokenValue() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void saveOrUpdateRefreshTokenValue(Member foundMember, String refreshTokenValue) throws AuthException {
        if(checkRefreshTokenExists(foundMember.getMemberPk()))
            updateRefreshTokenIfRefreshTokenValueExists(foundMember, refreshTokenValue);
        else
            saveRefreshTokenWithTokenValue(refreshTokenValue, foundMember.getMemberPk());
    }

    private boolean checkRefreshTokenExists(Long userPk) {
        return refreshTokenRepository.existsByMemberPk(userPk);
    }

    private void updateRefreshTokenIfRefreshTokenValueExists(Member foundMember, String refreshTokenValue) {
        if(refreshTokenRepository.existsByMemberPk(foundMember.getMemberPk())) {
            RefreshToken foundRefreshToken = findRefreshTokenByUserOrElseThrows(foundMember);
            updateRefreshTokenWithNewRefreshTokenValue(foundRefreshToken, refreshTokenValue);
        }
    }

    public RefreshToken findRefreshTokenByUserOrElseThrows(Member requestedUser) {
        return refreshTokenRepository.findByMemberPk(requestedUser.getMemberPk())
                .orElseThrow(() -> new AuthException(AuthErrorCode.UNSAVED_REFRESH_TOKEN));
    }

    private void updateRefreshTokenWithNewRefreshTokenValue(RefreshToken refreshToken, String refreshTokenValue) {
        RefreshToken updateRefreshToken = refreshToken.updateRefreshTokenValue(refreshTokenValue);
        refreshTokenRepository.save(updateRefreshToken);
    }

    private void saveRefreshTokenWithTokenValue(String refreshTokenValue, Long key) {
        RefreshToken refreshToken = RefreshToken.builder()
                .key(key)
                .refreshTokenValue(refreshTokenValue)
                .build();
        refreshTokenRepository.save(refreshToken);
    }

}
