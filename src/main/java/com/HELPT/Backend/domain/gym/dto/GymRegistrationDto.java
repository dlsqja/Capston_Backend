package com.HELPT.Backend.domain.gym.dto;

import com.HELPT.Backend.domain.gym.entity.GymRegistration;
import com.HELPT.Backend.domain.member.Dto.MemberDto;
import com.HELPT.Backend.domain.member.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymRegistrationDto {

    private String contactNumber; // 연락처
    private String businessNumber; // 사업자 등록번호
    private String businessType; // 사업자 구분
    private String ownerName; // 사업자 이름
    private String businessFile; // 사업자 등록증 파일 경로

    public GymRegistration toEntity()
    {
        return GymRegistration.builder()
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .businessType(businessType)
                .ownerName(ownerName)
                .businessFile(businessFile)
                .build();
    }

    public static GymRegistrationDto toDto(GymRegistration gymRegistration) {
        return GymRegistrationDto.builder()
                .contactNumber(gymRegistration.getContactNumber())
                .businessNumber(gymRegistration.getBusinessNumber())
                .businessType(gymRegistration.getBusinessType())
                .ownerName(gymRegistration.getOwnerName())
                .businessFile(gymRegistration.getBusinessFile())
                .build();
    }
}
