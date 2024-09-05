package com.HELPT.Backend.domain.equipment;

import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentDto> equipmentAdd(@RequestBody EquipmentDto equipmentDto){
        return ResponseEntity.ok(equipmentService.addEquipment(equipmentDto));
    }

    @GetMapping("/{equipment_id}")
    public ResponseEntity<EquipmentDto> equipmentDetails(@PathVariable Long equipment_id) {
        return ResponseEntity.ok(equipmentService.findEquipment(equipment_id));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDto>> equipmentList() {
        return ResponseEntity.ok(equipmentService.findEquipments());
    }

    @PutMapping("/{equipment_id}")
    public ResponseEntity<EquipmentDto> equipmentModify(@PathVariable Long equipment_id, @RequestBody EquipmentDto equipmentDto) {
        return ResponseEntity.ok(equipmentService.modifyEquipment(equipment_id, equipmentDto));
    }

    @DeleteMapping("/{equipment_id}")
    public ResponseEntity<Void> equipmentRemove(@PathVariable Long equipment_id) {
        equipmentService.removeEquipment(equipment_id);
        return ResponseEntity.ok().build();
    }
}
