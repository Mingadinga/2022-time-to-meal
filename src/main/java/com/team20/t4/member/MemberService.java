package com.team20.t4.member;

import com.team20.t4.common.exception.RequestErrorCode;
import com.team20.t4.common.exception.RequestException;
import com.team20.t4.common.s3.S3Util;
import com.team20.t4.member.domain.*;
import com.team20.t4.member.dto.MemberInfoResponseDto;
import com.team20.t4.member.dto.MemberLoginRequestDto;
import com.team20.t4.member.dto.MemberSaveRequestDto;
import com.team20.t4.member.dto.TokenRequestDto;
import com.team20.t4.security.JwtProvider;
import com.team20.t4.security.SecurityUtil;
import com.team20.t4.security.dto.TokenDto;
import com.team20.t4.security.exception.AuthErrorCode;
import com.team20.t4.security.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    public final MemberRepository memberRepository;
    private final MemberProfileImgRepository memberProfileImgRepository;
    private final PasswordEncoder pwdEncorder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final S3Util s3Util;

    public Member getLoginedMember(){
        return SecurityUtil.getCurrentUserPk().flatMap(memberRepository::findById)
                .orElseThrow(() -> new RequestException(RequestErrorCode.NOT_FOUND, "회원가입하지 않은 아이디입니다."));
    }

    @Transactional
    public void signup(MemberSaveRequestDto memberSaveRequestDto){
        checkMemberIdDuplicate(memberSaveRequestDto.getMemberId());
        memberRepository.save(memberSaveRequestDto.toEntityWithEncodedPassword(pwdEncorder));
    }

    private void checkMemberIdDuplicate(String memberId) {
        if(memberRepository.existsByMemberId(memberId))
            throw new RequestException(RequestErrorCode.DUPLICATED, "이미 회원가입한 아이디입니다.");
    }

    @Transactional
    public TokenDto login(MemberLoginRequestDto memberLoginRequestDto) throws RequestException{
        Member member = getMember(memberLoginRequestDto.getMemberId());
        checkPassword(memberLoginRequestDto.getPassword(), member.getPassword());
        return createTokenDtoAndUpdateRefreshTokenValue(member);
    }

    private Member getMember(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RequestException(RequestErrorCode.NOT_FOUND, "회원가입하지 않은 아이디입니다."));
    }

    private void checkPassword(String requestPassword, String encodedOriginPassword) {
        boolean isNotCorrectPassword = !(pwdEncorder.matches(requestPassword, encodedOriginPassword));
        if(isNotCorrectPassword) throw new RequestException(RequestErrorCode.WRONG_PASSWORD);
    }

    private TokenDto createTokenDtoAndUpdateRefreshTokenValue(Member validMember) throws RequestException {
        String accessToken = jwtProvider.createAccessToken(validMember.getMemberPk(), validMember.getRoles());
        String refreshToken = refreshTokenService.updateRefreshToken(validMember);
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) throws RequestException, AuthException {
        String requestedAccessToken = tokenRequestDto.getAccessToken();
        String requestedRefreshToken = tokenRequestDto.getRefreshToken();

        Member requestedMember = findUserById(getRequestedUserIdFromAccessToken(requestedAccessToken));
        RefreshToken foundRefreshToken = refreshTokenService.findRefreshTokenByUserOrElseThrows(requestedMember);
        checkRequestedRefreshTokenMatchesToFoundRefreshToken(requestedRefreshToken, foundRefreshToken);

        String accessToken = jwtProvider.createAccessToken(requestedMember.getMemberPk(), requestedMember.getRoles());
        String refreshToken = refreshTokenService.updateRefreshToken(requestedMember);

        return new TokenDto(accessToken, refreshToken);
    }

    public Member findUserById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RequestException(RequestErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
    }

    private Long getRequestedUserIdFromAccessToken(String requestedAccessToken) {
        try {
            return Long.parseLong(jwtProvider.getAuthentication(requestedAccessToken).getName());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AuthException(AuthErrorCode.WRONG_TOKEN);
        }
    }

    private void checkRequestedRefreshTokenMatchesToFoundRefreshToken(String requestedRefreshToken, RefreshToken foundRefreshToken) {
        boolean isNotValid = true;
        try {
            isNotValid = !foundRefreshToken.getRefreshTokenValue().equals(jwtProvider.getRefreshTokenValue(requestedRefreshToken));
        } catch (ExpiredJwtException e){
            log.error(e.getMessage());
            throw new AuthException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AuthException(AuthErrorCode.WRONG_TOKEN);
        }
        if (isNotValid)
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    @Transactional
    public MemberInfoResponseDto getMemberInfo(){
        Member loginedMember = getLoginedMember();
        return MemberInfoResponseDto.toDtoWithProfileImage(loginedMember, getImgUrl(loginedMember));
    }

    private String getImgUrl(Member loginedMember) {
        Optional<MemberProfileImg> optionalMemberProfileImg = memberProfileImgRepository.findByMember(loginedMember);
        String imgUrl;
        if(optionalMemberProfileImg.isEmpty()){
            imgUrl = s3Util.getUrl("member-profile-image/유튜브_기본프로필_하늘색.jpg");
        }
        else{
            imgUrl = s3Util.getUrl(optionalMemberProfileImg.get().getFileKey());
        }
        return imgUrl;
    }

    @Transactional
    public void updateMemberName(String newName){
        getLoginedMember().updateName(newName);
    }

    @Transactional
    public void updateMemberProfileImg(MultipartFile multipartFile){
        Member loginedMember = getLoginedMember();
        Optional<MemberProfileImg> optionalMemberProfileImg = memberProfileImgRepository.findByMember(loginedMember);
        if(optionalMemberProfileImg.isEmpty()){
            String imgUrl = s3Util.uploadFileV1("member-profile-image", multipartFile);
            memberProfileImgRepository.save(MemberProfileImg.builder().member(loginedMember).fileKey(imgUrl).build());
        }
        else{
            String uploadedFileKey = s3Util.updateFile(optionalMemberProfileImg.get().getFileKey(), "member-profile-image", multipartFile);
            optionalMemberProfileImg.get().updateFileKey(uploadedFileKey);
        }

    }

}
