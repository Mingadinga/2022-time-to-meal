package com.team20.t4.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberLoginRequestDto {
    @NotNull(message = "아이디는 빈값일 수 없습니다.") private String memberId;
    @NotNull(message = "비밀번호는 빈값일 수 없습니다.") private String password;

}
