package com.HELPT.Backend.domain.gym.dto;

import com.HELPT.Backend.domain.gym.entity.Address;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.GymRegistration;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymResistrationRequest {

    private Address address;
    private String gymName;

    private String contactNumber; // 연락처
    private String businessNumber; // 사업자 등록번호
    private String businessType; // 사업자 구분
    private String ownerName; // 사업자 이름
    private String businessFile; // 사업자 등록증 파일 경로

    public void updateBusinessFile(String file){
        this.businessFile = file;
    }

    public GymRegistration toGymRegistrationEntity()
    {
        return GymRegistration.builder()
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .businessType(businessType)
                .ownerName(ownerName)
                .businessFile(businessFile)
                .build();
    }

    public Gym toGymEntity(){
        return Gym.builder()
                .address(address)
                .gymName(gymName)
                .build();
    }

}
