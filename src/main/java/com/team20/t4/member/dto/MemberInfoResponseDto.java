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
    private Long memberPk;
    private String memberId;
    private String name;
    private List<String> roles;
    private String imgUrl;

    public static MemberInfoResponseDto toDtoWithProfileImage(Member member, String imgUrl){
        return MemberInfoResponseDto.builder()
                .memberPk(member.getMemberPk())
                .memberId(member.getMemberId())
                .name(member.getName())
                .roles(member.getRoles())
                .imgUrl(imgUrl)
                .build();
    }
}
