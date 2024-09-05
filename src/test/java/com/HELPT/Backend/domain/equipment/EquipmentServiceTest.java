package com.HELPT.Backend.domain.equipment;

import static org.junit.jupiter.api.Assertions.*;
import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @InjectMocks
    EquipmentService equipmentService;

    @Mock
    EquipmentRepository equipmentRepository;

    public Equipment setUpEquipment(){
        return Equipment.builder()
                .equipmentId(1L)
                .exerciseId(100L)
                .equipmentName("벤치 프레스")
                .defaultCount(10)
                .defaultSet(5)
                .defaultWeight(25)
                .build();
    }

    public List<Equipment> setUpEquipmentList(){
        List<Equipment> equipments = new ArrayList<>();
        Equipment equipment1 = Equipment.builder()
                .equipmentId(1L)
                .exerciseId(100L)
                .equipmentName("벤치프레스")
                .defaultCount(10)
                .defaultSet(5)
                .defaultWeight(25)
                .build();
        Equipment equipment2 = Equipment.builder()
                .equipmentId(2L)
                .exerciseId(200L)
                .equipmentName("스쿼트")
                .defaultCount(10)
                .defaultSet(5)
                .defaultWeight(25)
                .build();
        equipments.add(equipment1);
        equipments.add(equipment2);
        return equipments;
    }

    public EquipmentDto setUpEquipmentDto(){
        return EquipmentDto.builder()
                .equipmentId(1L)
                .exerciseId(100L)
                .equipmentName("벤치 프레스")
                .defaultCount(10)
                .defaultSet(5)
                .defaultWeight(25)
                .build();
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 등록 테스트")
    void saveEquipmentServiceTest(){
        // given
        EquipmentDto equipmentDto = setUpEquipmentDto();
        given(equipmentRepository.save(any(Equipment.class))).willReturn(equipmentDto.toEntity());

        // when
        EquipmentDto savedEquipmentDto = equipmentService.addEquipment(equipmentDto);

        // then
        verify(equipmentRepository).save(any(Equipment.class));
        assertEquals(savedEquipmentDto.getEquipmentName(),equipmentDto.getEquipmentName());
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 상세 조회 테스트")
    void findEquipmentServiceTest(){
        // given
        Equipment equipment = setUpEquipment();
        given(equipmentRepository.findById(equipment.getEquipmentId())).willReturn(Optional.of(equipment));

        // when
        EquipmentDto result = equipmentService.findEquipment(equipment.getEquipmentId());

        // then
        assertNotNull(result);
        verify(equipmentRepository).findById(equipment.getEquipmentId());
        assertThat(result.getEquipmentName()).isEqualTo(equipment.getEquipmentName());
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 목록 조회 테스트")
    void findEquipmentListServiceTest(){
        // given
        List<Equipment> equipments = setUpEquipmentList();
        given(equipmentRepository.findAll()).willReturn(equipments);

        // when
        List<EquipmentDto> result = equipmentService.findEquipments();

        // then
        verify(equipmentRepository).findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 수정 테스트")
    void modifyEquipmenServiceTest(){
        // given
        Equipment equipment = setUpEquipment();
        EquipmentDto equipmentDto = setUpEquipmentDto();
        given(equipmentRepository.findById(equipment.getEquipmentId())).willReturn(Optional.of(equipment));

        // when
        EquipmentDto result = equipmentService.modifyEquipment(equipment.getEquipmentId(), equipmentDto);

        // then
        assertThat(result.getEquipmentName()).isEqualTo(equipmentDto.getEquipmentName());
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 삭제 테스트")
    void removeEquipmenServiceTest(){
        // given
        Equipment equipment = setUpEquipment();
        given(equipmentRepository.findById(equipment.getEquipmentId())).willReturn(Optional.of(equipment));

        // when
        equipmentService.removeEquipment(equipment.getEquipmentId());

        // then
        verify(equipmentRepository).delete(any(Equipment.class));
    }
}