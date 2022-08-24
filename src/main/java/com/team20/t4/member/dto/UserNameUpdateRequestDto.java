package com.team20.t4.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserNameUpdateRequestDto {
    @NotNull(message = "이름은 빈값일 수 없습니다.") private String newName;
}
