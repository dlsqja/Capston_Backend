package com.HELPT.Backend.domain.entry_log.repository;

import com.HELPT.Backend.domain.entry_log.EntryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryLogRepository extends JpaRepository<EntryLog,Long>, EntryLogRepositoryCustom{
}
