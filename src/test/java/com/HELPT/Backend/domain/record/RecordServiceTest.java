package com.HELPT.Backend.domain.record;

import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import com.HELPT.Backend.domain.gymequipment.GymEquipmentRepository;
import com.HELPT.Backend.domain.record.dto.RecordRequest;
import com.HELPT.Backend.domain.record.dto.RecordResponse;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @InjectMocks
    private RecordService recordService;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private GymEquipmentRepository gymEquipmentRepository;

    private Record record;
    private RecordRequest recordRequest;
    private GymEquipment gymEquipment;

    @BeforeEach
    void setUp() {
        gymEquipment = GymEquipment.builder()
                .id(1L)
                .build();

        record = Record.builder()
                .recordId(1L)
                .userId(1L)
                .gymEquipment(gymEquipment)
                .count(10)
                .setNumber(3)
                .weight(50)
                .recordDate(LocalDate.now())
                .successRate(100)
                .comment("Good workout")
                .recordTime("10:00 AM")
                .snapshotFile("snapshot.jpg")
                .build();

        recordRequest = RecordRequest.builder()
                .gymEquipmentId(1L)
                .count(10)
                .setNumber(3)
                .weight(50)
                .successRate(100)
                .comment("Good workout")
                .recordTime("10:00 AM")
                .snapshotFile("snapshot.jpg")
                .build();
    }

    @Test
    @DisplayName("[Service] 운동 기록 생성 테스트")
    void saveRecordServiceTest() {
        // given
        given(gymEquipmentRepository.findById(anyLong())).willReturn(Optional.of(gymEquipment));
        given(recordRepository.save(any(Record.class))).willReturn(record);

        // when
        RecordResponse response = recordService.saveRecord(1L, recordRequest);

        // then
        verify(gymEquipmentRepository).findById(anyLong());
        verify(recordRepository).save(any(Record.class));
        assertNotNull(response);
    }

    @Test
    @DisplayName("[Service] 날짜별 운동기록 조회 테스트")
    void detailServiceTest() {
        // given
        List<Record> records = Collections.singletonList(record);
        given(recordRepository.findAllByUserIdAndRecordDate(anyLong(), any(LocalDate.class))).willReturn(Optional.of(records));

        // when
        List<RecordResponse> responses = recordService.detail(1L, LocalDate.now());

        // then
        verify(recordRepository).findAllByUserIdAndRecordDate(anyLong(), any(LocalDate.class));
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertThat(responses.size()).isEqualTo(1);
    }

}
