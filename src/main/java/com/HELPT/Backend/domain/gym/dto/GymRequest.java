package com.HELPT.Backend.domain.gym.dto;

import com.HELPT.Backend.domain.gym.entity.Address;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymRequest {

    private Address address;
    private String gymName;


    public Gym toEntity(){
        return Gym.builder()
                .address(address)
                .gymName(gymName)
                .build();
    }
}
