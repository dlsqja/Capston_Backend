package com.HELPT.Backend.domain.gym;

import com.HELPT.Backend.domain.gym.dto.GymRegistrationDto;
import com.HELPT.Backend.domain.gym.dto.GymResistrationRequest;
import com.HELPT.Backend.domain.gym.dto.GymResponse;
import com.HELPT.Backend.domain.gym.dto.GymRequest;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.GymRegistration;
import com.HELPT.Backend.domain.gym.entity.Status;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.manager.Manager;
import com.HELPT.Backend.domain.manager.ManagerRepository;
import com.HELPT.Backend.global.error.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.HELPT.Backend.domain.gym.entity.Status.Unregistered;
import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;
import static com.HELPT.Backend.global.error.ErrorCode.NOT_EXIST_DATA;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymService {

    private final GymRepository gymRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public GymResponse addGym(GymResistrationRequest gymInfo) {
        Gym gym = gymInfo.toGymEntity();
        GymRegistration gymRegistrationEntity = gymInfo.toGymRegistrationEntity();
        gym.updateGymRegistration(gymRegistrationEntity);
        gymRepository.save(gym);
        Optional<Manager> manager = managerRepository.findById(getCurrentUserId());
        manager.get().updateGym(gym);
        return GymResponse.toDto(gym);
    }

    @Transactional
    public GymResponse modifyChatLink(Long gymId,String link) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new RuntimeException("Gym not found"));
        gym.updateChatLink(link);
        return GymResponse.toDto(gym);
    }

    @Transactional(readOnly = true)
    public String getChatLink(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new RuntimeException("Gym not found"));
        return gym.getChat_link();
    }

    @Transactional
    public Boolean removeChatLink(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new RuntimeException("Gym not found"));
        gym.updateChatLink(null);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public GymResponse findGym(Long id) {
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_EXIST_DATA));
        return GymResponse.toDto(gym);
    }

    @Transactional(readOnly = true)
    public List<Gym> findGymList() {
        List<Gym> gymList = gymRepository.findAll();
        return gymList;
    }

    @Transactional(readOnly = true)
    public GymRegistrationDto findGymRegistration(Long id) {
        Gym gym = gymRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
        return GymRegistrationDto.toDto(gym.getGymRegistration());
    }


    @Transactional(readOnly = true)
    public List<GymResponse> findGyms() {
        List<Gym> gyms = gymRepository.findAll();
        return gyms.stream()
                .map(gym -> GymResponse.toDto(gym))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GymResponse findGymStatus(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new RuntimeException("Manager not found"));
        Gym gym = manager.getGym();
        if(gym == null){
            return GymResponse.builder()
                    .status(Unregistered)
                    .build();
        }
        return GymResponse.builder()
                .status(gym.getStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public List<Gym> findGymsByStatus(Status status) {
        return gymRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<GymResponse> findGymsByName(String name) {
        List<Gym> gyms;
        if (name == null || name.isEmpty()) {
            gyms = gymRepository.findAll();
        } else {
            gyms = gymRepository.findByGymNameContaining(name);
        }
        return gyms.stream()
                .map(gym -> GymResponse.toDto(gym))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateGymStatus(Long gymId, Status status) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new EntityNotFoundException("Gym not found with id " + gymId));
        gym.updateStatus(status);
        gymRepository.save(gym);
    }

    @Transactional
    public GymResponse modifyGym(Long id, GymRequest gymRequest) {
        Gym gym = gymRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
//        gym.updateAddress(gymRequest.getAddress());
        gym.updateGymName(gymRequest.getGymName());
//        gym = gymRepository.save(gym); // 변경 감지
        return GymResponse.toDto(gym);
    }

    @Transactional
    public void removeGym(Long id) {
        Gym gym = gymRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
        gymRepository.delete(gym);
    }
}
