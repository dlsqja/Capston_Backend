package com.HELPT.Backend.domain.record.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordRequest {

    private Long gymEquipmentId;

    private int count;

    private int setNumber;

    private int weight;

    private String recordTime;

    private float successRate;

    private String comment;

    private String snapshotFile;

    @Builder
    public RecordRequest(Long gymEquipmentId, int count, int setNumber, int weight, LocalDate recordDate, float successRate,
                         String recordTime ,String comment,String snapshotFile) {
        this.recordTime = recordTime;
        this.gymEquipmentId = gymEquipmentId;
        this.count = count;
        this.setNumber = setNumber;
        this.weight = weight;
        this.successRate = successRate;
        this.comment=comment;
        this.snapshotFile=snapshotFile;
    }

    public void setSnapshotFile(String snapshotFile) {
        this.snapshotFile = snapshotFile;
    }
}
