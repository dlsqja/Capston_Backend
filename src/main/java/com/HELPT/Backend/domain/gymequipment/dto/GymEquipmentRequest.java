package com.HELPT.Backend.domain.gymequipment.dto;

import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymEquipmentRequest {

    private Long equipmentId;

    private Long gymId;

    private int customCount;

    private int customSet;

    private int customWeight;

    public GymEquipment toEntity(){
        return GymEquipment.builder()
                .customCount(customCount)
                .customSet(customSet)
                .customWeight(customWeight)
                .build();
    }
}
