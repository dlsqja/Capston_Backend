package com.HELPT.Backend.domain.record;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.equipment.EquipmentRepository;
import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import com.HELPT.Backend.domain.gymequipment.GymEquipmentRepository;
import com.HELPT.Backend.domain.product.Product;
import com.HELPT.Backend.domain.product.ProductRepository;
import com.HELPT.Backend.domain.product.dto.ProductResponse;
import com.HELPT.Backend.domain.record.dto.RecordRequest;
import com.HELPT.Backend.domain.record.dto.RecordResponse;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecordService {

    private final RecordRepository recordRepository;
    private final GymEquipmentRepository gymEquipmentRepository;

    public RecordResponse saveRecord(Long userId, RecordRequest recordRequest) {
        Optional<GymEquipment> equipment = gymEquipmentRepository.findById(recordRequest.getGymEquipmentId());
        if(equipment.isEmpty()){
            new CustomException(ErrorCode.NOT_EXIST_DATA);
        }
        Record saveRecord = Record.builder()
                .userId(userId)
                .gymEquipment(equipment.get())
                .count(recordRequest.getCount())
                .setNumber(recordRequest.getSetNumber())
                .weight(recordRequest.getWeight())
                .recordDate(LocalDate.now())
                .successRate(recordRequest.getSuccessRate())
                .comment(recordRequest.getComment())
                .recordTime(recordRequest.getRecordTime())
                .snapshotFile(recordRequest.getSnapshotFile())
                .build();

        recordRepository.save(saveRecord);

        return new RecordResponse(saveRecord);
    }

    public List<RecordResponse> detail(Long userId, LocalDate monthDate) {

        List<Record> findRecord = recordRepository.findAllByUserIdAndRecordDate(userId,monthDate).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_DATA));

        return findRecord.stream().map(RecordResponse::new).toList();
    }

    /*
    public List<RecordResponse> recordList(Long userId, LocalDate monthDate) {

        LocalDate firstDayOfMonth = monthDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = monthDate.with(TemporalAdjusters.lastDayOfMonth());

        Optional<List<Record>> findRecord = recordRepository.findAllByUserIdAndRecordDateBetween(userId, firstDayOfMonth,lastDayOfMonth);

        if(findRecord.isPresent())
        {
            List<Record> listOfRecords = findRecord.get();
            List<RecordResponse> recordResponsesList = listOfRecords.stream().map(RecordResponse::new).toList();

            return recordResponsesList;
        }
        else {
            return null;
        }
    }
*/

}
