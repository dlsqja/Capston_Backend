package com.HELPT.Backend.domain.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {

    Optional<List<Record>> findAllByUserIdAndRecordDateBetween(Long userId, LocalDate recordDate, LocalDate recordDate2);

    Optional<List<Record>> findAllByUserIdAndRecordDate(Long userId,LocalDate recordDate);
}
