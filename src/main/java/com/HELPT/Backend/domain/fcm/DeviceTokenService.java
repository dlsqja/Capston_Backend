package com.HELPT.Backend.domain.fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    public void saveDeviceToken(Long userId, String deviceToken) {
        List<DeviceToken> tokenList = deviceTokenRepository.findAllByUserId(userId);
        for (DeviceToken token : tokenList) {
            if(token.equals(deviceToken)) return;
        }
        DeviceToken deviceTokenEntity = DeviceToken.builder()
                .userId(userId)
                .deviceToken(deviceToken)
                .build();
        deviceTokenRepository.save(deviceTokenEntity);
    }

    public List<String> getDeviceTokensByGymId(Long gymId) {
        return deviceTokenRepository.findDeviceTokensByGymId(gymId);
    }
}