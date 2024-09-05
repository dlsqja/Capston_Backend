package com.HELPT.Backend.domain.exercise;

import com.HELPT.Backend.domain.exercise.Exercise;
import com.HELPT.Backend.domain.exercise.dto.ExerciseRequestDto;
import com.HELPT.Backend.domain.exercise.dto.ExerciseResponseDto;
import com.HELPT.Backend.domain.exercise.repository.ExerciseRepository;
import com.HELPT.Backend.domain.gymequipment.GymEquipment;
import com.HELPT.Backend.domain.gymequipment.GymEquipmentRepository;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final GymEquipmentRepository gymEquipmentRepository;

    @Transactional
    public ExerciseResponseDto uploadExercise(ExerciseRequestDto exerciseRequestDto, String topImage) {
        Exercise exercise = Exercise.builder()
                .exerciseDescription(exerciseRequestDto.getExerciseDescription())
                .exerciseMethod(exerciseRequestDto.getExerciseMethod())
                .topImage(topImage)
                .build();
        exerciseRepository.save(exercise);

        log.info("response 생성");
        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto(exercise);
        log.info("response 생성 완료");
        return exerciseResponseDto;
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDto findExercise(Long gymEquipmentId){
        Optional<GymEquipment> gymEquipment = gymEquipmentRepository.findById(gymEquipmentId);
        log.info(gymEquipment.get().getGym().getGymName());
        Optional<Exercise> exercise = exerciseRepository.findById(gymEquipment.get().getEquipment().getExerciseId());
        if(exercise.isEmpty()){
            new CustomException(ErrorCode.NOT_EXIST_DATA);
        }
        return new ExerciseResponseDto(exercise.get());
    }

    @Transactional(readOnly = true)
    public Exercise findNonParsingExercise(Long exerciseId){
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if(exercise.isEmpty()){
            new CustomException(ErrorCode.NOT_EXIST_DATA);
        }
        return exercise.get();
    }

    @Transactional
    public ExerciseResponseDto modifyExercise(Long exerciseId,ExerciseRequestDto exerciseRequestDto, String topImage){
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if(exercise.isEmpty()){
            new CustomException(ErrorCode.NOT_EXIST_DATA);
        }
        exercise.get().updateDescription(exerciseRequestDto.getExerciseDescription());
        exercise.get().updateMetood(exerciseRequestDto.getExerciseMethod());
        exercise.get().updatetopImage(topImage);
        return new ExerciseResponseDto(exercise.get());
    }
}
