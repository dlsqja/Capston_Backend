package com.HELPT.Backend.domain.entry_log;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/entry-logs")
@RequiredArgsConstructor
public class EntryLogController {

    private final EntryLogService entryLogService;

    @GetMapping
    public ResponseEntity<List<EntryLogResponse>> getEntryLogs(
            @RequestParam String name,
            @RequestParam Long gymId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(entryLogService.getEntryLogsByNameAndDate(name, gymId, date));
    }

}
