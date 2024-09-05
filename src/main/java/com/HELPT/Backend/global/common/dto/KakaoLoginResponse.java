package com.HELPT.Backend.global.common.dto;

import com.HELPT.Backend.domain.gym.entity.Status;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginResponse {

    private String accessToken;
    private String refreshToken;
    private Status gymStatus;
}
