package com.team20.t4.member;

import com.team20.t4.common.responseFormat.OnlyResponseString;
import com.team20.t4.member.dto.*;
import com.team20.t4.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {
    public final MemberService memberService;

    @PostMapping("/member/signup")
    public OnlyResponseString join(@RequestBody @Valid MemberSaveRequestDto requestDto){
        memberService.signup(requestDto);
        return new OnlyResponseString("회원가입에 성공했습니다.");
    }

    @PostMapping("/member/login")
    public TokenDto login(@RequestBody @Valid MemberLoginRequestDto requestDto){
        return memberService.login(requestDto);
    }

    @PostMapping("/member/reissue")
    public TokenDto reissue(@RequestBody @Valid TokenRequestDto requestDto){
        return memberService.reissue(requestDto);
    }

    @GetMapping("/member/info")
    public MemberInfoResponseDto getInfo(){
        return memberService.getMemberInfo();
    }

    @PostMapping("/member/info/name")
    public OnlyResponseString updateMemberName(@RequestBody @Valid UserNameUpdateRequestDto requestDto){
        memberService.updateMemberName(requestDto.getNewName());
        return new OnlyResponseString("회원 이름 수정에 성공했습니다.");
    }

}
