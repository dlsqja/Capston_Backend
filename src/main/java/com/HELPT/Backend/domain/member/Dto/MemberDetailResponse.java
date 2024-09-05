package com.HELPT.Backend.domain.member.Dto;

import com.HELPT.Backend.global.auth.GENDER;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetailResponse {

    private String userName;
    private GENDER gender;
    private float height;
    private float weight;
    private Long membershipId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String profileImage;
    private LocalDate birthDate;

}
