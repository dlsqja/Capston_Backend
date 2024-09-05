package com.HELPT.Backend.domain.exercise.repository;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

}
