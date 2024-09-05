package com.HELPT.Backend.domain.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * FCM 전송 Format DTO
 *
 * @author : lee
 * @fileName : FcmMessageDto
 * @since : 2/21/24
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FcmMessageDto {

    @JsonProperty("validate_only")
    private boolean validateOnly;

    @JsonProperty("message")
    private FcmMessageDto.Message message;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Message {

        @JsonProperty("notification")
        private FcmMessageDto.Notification notification;

        @JsonProperty("token")
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Notification {

        @JsonProperty("title")
        private String title;

        @JsonProperty("body")
        private String body;

        @JsonProperty("image")
        private String image;
    }
}