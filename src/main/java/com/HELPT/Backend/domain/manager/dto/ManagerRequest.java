package com.HELPT.Backend.domain.manager.dto;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.manager.Manager;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerRequest {

    private String kakaoId;

    public Manager toEntity(){
        return Manager.builder()
                .kakaoId(kakaoId)
                .build();
    }
}
