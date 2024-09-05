package com.HELPT.Backend.domain.equipment;

import com.HELPT.Backend.domain.exercise.Exercise;
import com.HELPT.Backend.domain.gym.entity.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;

    private Long exerciseId;

    private String equipmentName;

    private String equipmentNameEng;

    private int defaultCount;

    private int defaultSet;

    private int defaultWeight;

    public void updateEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void updateDefaultCount(int defaultCount) {
        this.defaultCount = defaultCount;
    }

    public void updateDefaultSet(int defaultSet) {
        this.defaultSet = defaultSet;
    }

    public void updateDefaultWeight(int defaultWeight) {
        this.defaultWeight = defaultWeight;
    }
}
