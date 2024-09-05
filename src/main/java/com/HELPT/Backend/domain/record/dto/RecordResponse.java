package com.HELPT.Backend.domain.record.dto;

import com.HELPT.Backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordResponse {

    private String equipmentName;

    private int count;

    private int setNumber;

    private int weight;

    private String recordTime;

    private LocalDate recordDate;

    private float successRate;

    private String comment;

    private String snapshotFile;

    public RecordResponse(Record record) {
        this.count = record.getCount();
        this.setNumber = record.getSetNumber();
        this.weight = record.getWeight();
        this.recordDate = record.getRecordDate();
        this.successRate = record.getSuccessRate();
        this.recordTime = record.getRecordTime();
        this.comment=record.getComment();
        this.snapshotFile=record.getSnapshotFile();
        this.equipmentName = record.getGymEquipment().getEquipment().getEquipmentName();
    }
}
