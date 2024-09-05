package com.HELPT.Backend.domain.exercise;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.exercise.dto.ExerciseRequestDto;
import com.HELPT.Backend.domain.exercise.dto.ExerciseResponseDto;
import com.HELPT.Backend.domain.exercise.repository.ExerciseRepository;
import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import com.HELPT.Backend.domain.gymequipment.GymEquipmentRepository;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @InjectMocks
    private ExerciseService exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private GymEquipmentRepository gymEquipmentRepository;

    private Exercise exercise;
    private ExerciseRequestDto exerciseRequestDto;
    private GymEquipment gymEquipment;

    @BeforeEach
    void setUp() {
        exercise = Exercise.builder()
                .exerciseId(1L)
                .exerciseDescription("Description")
                .exerciseMethod("Method")
                .topImage("topImage.jpg")
                .build();

        exerciseRequestDto = ExerciseRequestDto.builder()
                .exerciseDescription("New Description")
                .exerciseMethod("New Method")
                .build();

        gymEquipment = GymEquipment.builder()
                .id(1L)
                .equipment(Equipment.builder().exerciseId(1L).build())
                .build();
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 운동 정보 등록 테스트")
    void uploadExerciseTest() {
        // given
        given(exerciseRepository.save(any(Exercise.class))).willReturn(exercise);

        // when
        ExerciseResponseDto response = exerciseService.uploadExercise(exerciseRequestDto, "topImage.jpg");

        // then
        verify(exerciseRepository).save(any(Exercise.class));
        assertNotNull(response);
        assertThat(response.getExerciseDescription()).isEqualTo(exercise.getExerciseDescription());
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 운동 정보 조회 테스트")
    void findExerciseTest() {
        // given
        given(gymEquipmentRepository.findById(anyLong())).willReturn(Optional.of(gymEquipment));
        given(exerciseRepository.findById(anyLong())).willReturn(Optional.of(exercise));

        // when
        ExerciseResponseDto response = exerciseService.findExercise(1L);

        // then
        verify(gymEquipmentRepository).findById(anyLong());
        verify(exerciseRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getExerciseDescription()).isEqualTo(exercise.getExerciseDescription());
    }

    @Test
    @DisplayName("[Service] AI 코칭 기구 운동 정보 수정 테스트")
    void modifyExerciseTest() {
        // given
        given(exerciseRepository.findById(anyLong())).willReturn(Optional.of(exercise));

        // when
        ExerciseResponseDto response = exerciseService.modifyExercise(1L, exerciseRequestDto, "topImage.jpg");

        // then
        verify(exerciseRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getExerciseDescription()).isEqualTo(exerciseRequestDto.getExerciseDescription());
    }
}
