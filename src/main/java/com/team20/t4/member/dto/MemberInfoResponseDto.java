package com.team20.t4.member.dto;

import com.team20.t4.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private Long id;
    private String memberId;
    private String name;
    private List<String> roles;

    public static MemberInfoResponseDto of(Member member){
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .roles(member.getRoles())
                .build();
    }
}
