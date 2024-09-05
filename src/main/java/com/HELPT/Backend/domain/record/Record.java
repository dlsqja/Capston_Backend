package com.HELPT.Backend.domain.record;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_equipment_id")
    private GymEquipment gymEquipment;

    private int count;

    private int setNumber;

    private int weight;

    private LocalDate recordDate;

    private String recordTime;

    private String comment;

    private float successRate;

    private String snapshotFile;
}
