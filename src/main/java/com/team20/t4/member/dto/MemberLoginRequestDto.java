package com.team20.t4.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberLoginRequestDto {
    @NotEmpty(message = "아이디는 빈값일 수 없습니다.") private String memberId;
    @NotEmpty(message = "비밀번호는 빈값일 수 없습니다.") private String password;

}
