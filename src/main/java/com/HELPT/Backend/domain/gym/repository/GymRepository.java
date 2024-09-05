package com.HELPT.Backend.domain.gym.repository;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym,Long> {
    List<Gym> findByStatus(Status status);
    Long findByGymName(String gymName);
    List<Gym> findByGymNameContaining(String name);
}
