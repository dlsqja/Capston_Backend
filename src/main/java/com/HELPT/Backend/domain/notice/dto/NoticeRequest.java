package com.HELPT.Backend.domain.notice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeRequest {

    private Long gymId;

    private String title;

    private String content;

    @Builder
    public NoticeRequest(Long gymId, String title, String content, LocalDate createAt) {
        this.gymId = gymId;
        this.title = title;
        this.content = content;
    }
}
