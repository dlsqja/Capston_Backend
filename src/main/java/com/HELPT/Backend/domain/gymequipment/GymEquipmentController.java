package com.HELPT.Backend.domain.gymequipment;

import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentRequest;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentResponse;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gym-equipments")
public class GymEquipmentController {

    private final GymEquipmentService gymEquipmentService;

    @PostMapping
    public ResponseEntity<GymEquipmentResponse> gymEquipmentAdd(@RequestBody GymEquipmentRequest gymEquipmentRequest) {
        GymEquipmentResponse savedGymEquipment = gymEquipmentService.addGymEquipment(gymEquipmentRequest);
        return ResponseEntity.ok(savedGymEquipment);
    }

    @GetMapping("/{gymEquipmentId}")
    public ResponseEntity<GymEquipmentResponse> getGymEquipment(@PathVariable Long gymEquipmentId) {
        GymEquipmentResponse gymEquipment = gymEquipmentService.findGymEquipment(gymEquipmentId);
        return ResponseEntity.ok(gymEquipment);
    }

    @PutMapping("/{gymEquipmentId}")
    public ResponseEntity<GymEquipmentResponse> gymEquipmentModify(@PathVariable Long gymEquipmentId, @RequestBody GymEquipmentUpdateRequest gymEquipmentUpdateRequest) {
        GymEquipmentResponse updatedGymEquipment = gymEquipmentService.modifyGymEquipment(gymEquipmentId, gymEquipmentUpdateRequest);
        return ResponseEntity.ok(updatedGymEquipment);
    }


    @GetMapping("/{gymId}/unlinkedEquipments")
    public ResponseEntity<List<EquipmentDto>> getGymUnlinkedEquipments(@PathVariable Long gymId) {
        List<EquipmentDto> equipments = gymEquipmentService.findUnlinkEquipments(gymId);
        return ResponseEntity.ok(equipments);
    }


    @DeleteMapping("/{gymEquipmentId}")
    public ResponseEntity<Void> GymEquipment(@PathVariable Long gymEquipmentId) {
        gymEquipmentService.removeGymEquipment(gymEquipmentId);
        return ResponseEntity.ok().build();
    }

}
