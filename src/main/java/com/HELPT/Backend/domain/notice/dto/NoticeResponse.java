package com.HELPT.Backend.domain.notice.dto;

import com.HELPT.Backend.domain.notice.Notice;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponse {
    private Long noticeId;

    private String title;

    private String content;

    private LocalDate createAt;

    public NoticeResponse(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createAt = notice.getCreateAt();
    }



}
