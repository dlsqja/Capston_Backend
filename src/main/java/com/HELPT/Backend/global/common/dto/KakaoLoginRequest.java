package com.HELPT.Backend.global.common.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginRequest {

    private String kakaoId;
    private String deviceToken;
}
