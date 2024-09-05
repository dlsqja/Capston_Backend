package com.HELPT.Backend.domain.gymequipment;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.equipment.EquipmentRepository;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentRequest;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentResponse;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GymEquipmentServiceTest {

    @InjectMocks
    private GymEquipmentService gymEquipmentService;

    @Mock
    private GymEquipmentRepository gymEquipmentRepository;

    @Mock
    private GymRepository gymRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    private Gym gym;
    private Equipment equipment;
    private GymEquipment gymEquipment;

    @BeforeEach
    void setUp() {
        gym = Gym.builder()
                .id(1L)
                .gymName("Test Gym")
                .build();

        equipment = Equipment.builder()
                .equipmentId(1L)
                .exerciseId(100L)
                .equipmentName("벤치 프레스")
                .defaultCount(10)
                .defaultSet(5)
                .defaultWeight(25)
                .build();

        gymEquipment = GymEquipment.builder()
                .id(1L)
                .gym(gym)
                .equipment(equipment)
                .build();
    }

    @Test
    @DisplayName("[Service] 헬스장 기구 등록 테스트")
    void addGymEquipmentTest() {
        // given
        GymEquipmentRequest request = GymEquipmentRequest.builder()
                .gymId(1L)
                .equipmentId(1L)
                .build();
        given(equipmentRepository.findById(anyLong())).willReturn(Optional.of(equipment));
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));
        given(gymEquipmentRepository.save(any(GymEquipment.class))).willReturn(gymEquipment);

        // when
        GymEquipmentResponse response = gymEquipmentService.addGymEquipment(request);

        // then
        verify(equipmentRepository).findById(anyLong());
        verify(gymRepository).findById(anyLong());
        verify(gymEquipmentRepository).save(any(GymEquipment.class));
        assertNotNull(response);
        assertThat(response.getGymEquipmentId()).isEqualTo(equipment.getEquipmentId());
    }

    @Test
    @DisplayName("[Service] 헬스장 기구 상세 조회 테스트")
    void findGymEquipmentTest() {
        // given
        given(gymEquipmentRepository.findById(anyLong())).willReturn(Optional.of(gymEquipment));

        // when
        GymEquipmentResponse response = gymEquipmentService.findGymEquipment(1L);

        // then
        verify(gymEquipmentRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getGymEquipmentId()).isEqualTo(equipment.getEquipmentId());
    }

    @Test
    @DisplayName("[Service] 헬스장 기구 목록 조회 테스트")
    void findGymEquipmentsTest() {
        // given
        given(gymEquipmentRepository.findByGym_Id(anyLong())).willReturn(Collections.singletonList(gymEquipment));

        // when
        List<GymEquipmentResponse> responses = gymEquipmentService.findGymEquipments(1L);

        // then
        verify(gymEquipmentRepository).findByGym_Id(anyLong());
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertThat(responses.get(0).getGymEquipmentId()).isEqualTo(equipment.getEquipmentId());
    }

    @Test
    @DisplayName("[Service] 헬스장 기구 수정 테스트")
    void modifyGymEquipmentTest() {
        // given
        GymEquipmentUpdateRequest updateRequest = GymEquipmentUpdateRequest.builder()
                .customCount(10)
                .customSet(10)
                .customWeight(30)
                .build();
        given(gymEquipmentRepository.findById(anyLong())).willReturn(Optional.of(gymEquipment));

        // when
        GymEquipmentResponse response = gymEquipmentService.modifyGymEquipment(1L, updateRequest);

        // then
        verify(gymEquipmentRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getGymEquipmentId()).isEqualTo(equipment.getEquipmentId());
    }

    @Test
    @DisplayName("[Service] 헬스장 기구 삭제 테스트")
    void removeGymEquipmentTest() {
        // given
        given(gymEquipmentRepository.findById(anyLong())).willReturn(Optional.of(gymEquipment));

        // when
        gymEquipmentService.removeGymEquipment(1L);

        // then
        verify(gymEquipmentRepository).findById(anyLong());
        verify(gymEquipmentRepository).deleteById(anyLong());
    }
}
