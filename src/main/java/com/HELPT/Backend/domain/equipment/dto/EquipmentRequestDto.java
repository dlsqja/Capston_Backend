package com.HELPT.Backend.domain.equipment.dto;

import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import com.HELPT.Backend.domain.exercise.dto.ExerciseRequestDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EquipmentRequestDto {
    private Long equipmentId;

    private Long exerciseId;

    private String equipmentName;

    private int defaultCount;

    private int defaultSet;

    private int defaultWeight;

    private String exerciseDescription;

    private String exerciseMethod;

    private String originImage;

    private MultipartFile topImage;

    public ExerciseRequestDto toExerciseDto(){
        return ExerciseRequestDto.builder()
                .exerciseDescription(exerciseDescription)
                .exerciseMethod(exerciseMethod)
                .build();
    }

    public EquipmentDto toEquipmentDto(){
        return EquipmentDto.builder()
                .exerciseId(exerciseId)
                .equipmentName(equipmentName)
                .defaultSet(defaultSet)
                .defaultCount(defaultCount)
                .defaultWeight(defaultWeight)
                .build();
    }
}
