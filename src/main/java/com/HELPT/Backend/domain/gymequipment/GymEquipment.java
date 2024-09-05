package com.HELPT.Backend.domain.gymequipment;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentRequest;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private int customCount;

    private int customSet;

    private int customWeight;

    public void updateGym(Gym gym){this.gym = gym;}
    public void updateEquipment(Equipment equipment){this.equipment = equipment;}
    public void updateGymEquipment(GymEquipmentUpdateRequest gymEquipmentUpdateRequest){
        this.customCount = gymEquipmentUpdateRequest.getCustomCount();
        this.customSet = gymEquipmentUpdateRequest.getCustomSet();
        this.customWeight = gymEquipmentUpdateRequest.getCustomWeight();
    }

}
