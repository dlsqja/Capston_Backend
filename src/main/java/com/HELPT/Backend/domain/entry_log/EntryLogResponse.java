package com.HELPT.Backend.domain.entry_log;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntryLogResponse {
    private String userName;
    private LocalDateTime entryTime;
}
