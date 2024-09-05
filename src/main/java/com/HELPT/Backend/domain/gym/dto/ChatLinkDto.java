package com.HELPT.Backend.domain.gym.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLinkDto {
    private String chatLink;
}
