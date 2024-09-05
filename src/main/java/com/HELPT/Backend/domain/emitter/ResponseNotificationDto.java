package com.HELPT.Backend.domain.emitter;

import com.HELPT.Backend.domain.emitter.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "알림 Dto")
@Getter
@Setter
public class ResponseNotificationDto {
    private Long id;
    private String content;
    private String url;
    private Boolean isRead;
    private LocalDateTime createdAt;

    @Builder
    public ResponseNotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.url = notification.getUrl();
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt();
    }

}