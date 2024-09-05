package com.HELPT.Backend.domain.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private Long gymId;

    private String title;

    private String content;

    private LocalDate createAt;

    @Builder
    public Notice(Long gymId, String title, String content, LocalDate createAt) {
        this.gymId = gymId;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
    }
}
