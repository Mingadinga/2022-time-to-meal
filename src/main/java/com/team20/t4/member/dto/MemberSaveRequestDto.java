package com.team20.t4.member.dto;

import com.team20.t4.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {
    @NotEmpty(message = "아이디는 빈값일 수 없습니다.") private String memberId;
    @NotEmpty(message = "이름은 빈값일 수 없습니다.") private String name;
    @NotEmpty(message = "비밀번호는 빈값일 수 없습니다.") private String password;

    public Member toEntityWithEncodedPassword(PasswordEncoder passwordEncoder){
        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .name(name)
                .build();
    }
}
