package com.HELPT.Backend.domain.gymequipment.dto;

import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymEquipmentResponse {

    private Long gymEquipmentId;

    private String equipmentName;

    private String equipmentNameEng;

    private int customCount;

    private int customSet;

    private int customWeight;

    public static GymEquipmentResponse toDto(GymEquipment gymEquipment){
        return GymEquipmentResponse.builder()
                .gymEquipmentId(gymEquipment.getId())
                .equipmentName(gymEquipment.getEquipment().getEquipmentName())
                .customCount(gymEquipment.getCustomCount())
                .customSet(gymEquipment.getCustomSet())
                .customWeight(gymEquipment.getCustomWeight())
                .equipmentNameEng(gymEquipment.getEquipment().getEquipmentNameEng())
                .build();
    }

}
