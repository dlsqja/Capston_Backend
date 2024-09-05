package com.HELPT.Backend.domain.entry_log.repository;

import com.HELPT.Backend.domain.entry_log.EntryLog;
import com.HELPT.Backend.domain.entry_log.EntryLogResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EntryLogRepositoryCustom {
    List<EntryLogResponse> findByNameAndGymIdAndEntryDate(String name, Long gymId, LocalDate date);
}