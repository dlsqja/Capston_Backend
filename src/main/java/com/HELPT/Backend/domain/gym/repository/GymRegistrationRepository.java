package com.HELPT.Backend.domain.gym.repository;

import com.HELPT.Backend.domain.gym.entity.GymRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRegistrationRepository extends JpaRepository<GymRegistration,Long> {
}
