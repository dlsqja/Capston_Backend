package com.HELPT.Backend.domain.gymequipment;

import com.HELPT.Backend.domain.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymEquipmentRepository extends JpaRepository<GymEquipment,Long> {

    List<GymEquipment> findByGym_Id(Long gymId);

    @Query("SELECT e FROM Equipment e WHERE e.equipmentId NOT IN (SELECT ge.equipment.equipmentId FROM GymEquipment ge WHERE ge.gym.id = :gymId)")
    List<Equipment> findEquipments(@Param("gymId") Long gymId);
}
