package com.HELPT.Backend.domain.fcm;

import lombok.*;

/**
 * FCM 전송을 위한 디바이스 정보 조회 DTO
 *
 * @author : lee
 * @fileName : FcmSendDeviceDto
 * @since : 2/26/24
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDeviceDto {

    private String dvcTkn;

    @Builder
    public FcmSendDeviceDto(String dvcTkn) {
        this.dvcTkn = dvcTkn;
    }
}
