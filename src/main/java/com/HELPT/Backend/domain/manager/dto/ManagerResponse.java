package com.HELPT.Backend.domain.manager.dto;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.manager.Manager;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerResponse {

    private Long gymId;

    @Builder
    public ManagerResponse(Manager manager) {
        this.gymId = manager.getGym().getId();
    }
}
