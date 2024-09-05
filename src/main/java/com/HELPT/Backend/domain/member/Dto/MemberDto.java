package com.HELPT.Backend.domain.member.Dto;

import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.global.auth.GENDER;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private Long gymId;
    private String userName;
    private GENDER gender;
    private float height;
    private float weight;
    private String kakaoId;
    private String profileImage;
    private LocalDate birthDate;

    public Member toEntity()
    {
        return Member.builder()
                .kakaoId(kakaoId)
                .gymId(gymId)
                .userName(userName)
                .gender(gender)
                .height(height)
                .weight(weight)
                .profileImage(profileImage)
                .birthDate(birthDate)
                .build();
    }

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .userName(member.getUserName())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .profileImage(member.getProfileImage())
                .birthDate(member.getBirthDate())
                .build();
    }
}
