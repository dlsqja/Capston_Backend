package com.HELPT.Backend.domain.gymequipment;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.equipment.EquipmentRepository;
import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentRequest;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentResponse;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymEquipmentService{

    private final GymEquipmentRepository gymEquipmentRepository;
    private final GymRepository gymRepository;
    private final EquipmentRepository equipmentRepository;

    public GymEquipmentResponse addGymEquipment(GymEquipmentRequest gymEquipmentRequest) {
        Equipment equipment = equipmentRepository.findById(gymEquipmentRequest.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Gym gym = gymRepository.findById(gymEquipmentRequest.getGymId())
                .orElseThrow(() -> new RuntimeException("Gym not found"));
        GymEquipment gymEquipment = gymEquipmentRequest.toEntity();
        gymEquipment.updateEquipment(equipment);
        gymEquipment.updateGym(gym);
        gymEquipment = gymEquipmentRepository.save(gymEquipment);
        return GymEquipmentResponse.toDto(gymEquipment);
    }

    public GymEquipmentResponse findGymEquipment(Long equipmentId) {
        GymEquipment gymEquipment = gymEquipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        return GymEquipmentResponse.toDto(gymEquipment);
    }

    public List<GymEquipmentResponse> findGymEquipments(Long gymId) {
        List<GymEquipment> equipments = gymEquipmentRepository.findByGym_Id(gymId);
        return equipments.stream().map(equipment->GymEquipmentResponse.toDto(equipment)).collect(Collectors.toList());
    }
    public List<EquipmentDto> findUnlinkEquipments(Long gymId) {
        List<Equipment> equipments = gymEquipmentRepository.findEquipments(gymId);
        return equipments.stream().map(EquipmentDto::toDto).collect(Collectors.toList());
    }
    public GymEquipmentResponse modifyGymEquipment(Long equipmentId, GymEquipmentUpdateRequest gymEquipmentUpdateRequest) {
        GymEquipment gymEquipment = gymEquipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        gymEquipment.updateGymEquipment(gymEquipmentUpdateRequest);
        return GymEquipmentResponse.toDto(gymEquipment);
    }

    public void removeGymEquipment(Long equipmentId) {
        gymEquipmentRepository.deleteById(equipmentId);
    }
}