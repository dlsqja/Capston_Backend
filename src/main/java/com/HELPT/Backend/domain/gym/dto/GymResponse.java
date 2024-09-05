package com.HELPT.Backend.domain.gym.dto;

import com.HELPT.Backend.domain.gym.entity.Address;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GymResponse {

    private Long gymId;
    private Address address;
    private String gymName;
    private Status status;

    public static GymResponse toDto(Gym gym){
        return GymResponse.builder()
                .gymId(gym.getId())
                .address(gym.getAddress())
                .gymName(gym.getGymName())
                .status(gym.getStatus())
                .build();
    }
}
