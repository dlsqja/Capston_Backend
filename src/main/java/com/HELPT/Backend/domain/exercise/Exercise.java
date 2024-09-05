package com.HELPT.Backend.domain.exercise;

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
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    private String exerciseDescription;

    private String exerciseMethod;

    private String topImage;

    private String videoUrl;

    public void updateDescription(String equipmentName) {
        this.exerciseDescription = equipmentName;
    }
    public void updateMetood(String exerciseMethod) { this.exerciseDescription = exerciseMethod; }
    public void updatetopImage(String topImage) {
        this.topImage = topImage;
    }

}
