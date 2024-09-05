package com.HELPT.Backend.domain.fcm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findAllByUserId(Long userId);

    @Query("SELECT dt.deviceToken FROM DeviceToken dt JOIN Member m ON dt.userId = m.userId WHERE m.gymId = :gymId")
    List<String> findDeviceTokensByGymId(@Param("gymId") Long gymId);
}