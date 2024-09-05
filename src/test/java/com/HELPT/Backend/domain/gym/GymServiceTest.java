package com.HELPT.Backend.domain.gym;

import com.HELPT.Backend.domain.gym.dto.GymRegistrationDto;
import com.HELPT.Backend.domain.gym.dto.GymRequest;
import com.HELPT.Backend.domain.gym.dto.GymResistrationRequest;
import com.HELPT.Backend.domain.gym.dto.GymResponse;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.manager.Manager;
import com.HELPT.Backend.domain.manager.ManagerRepository;
import com.HELPT.Backend.global.error.CustomException;
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

import static com.HELPT.Backend.global.error.ErrorCode.NOT_EXIST_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GymServiceTest {

    @Mock
    private GymRepository gymRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private GymService gymService;

    private Gym gym;
    private Manager manager;

    @BeforeEach
    void setUp() {
        gym = Gym.builder()
                .id(1L)
                .gymName("Test Gym")
                .build();

        manager = Manager.builder()
                .managerId(1L)
                .gym(gym)
                .build();
    }

    public GymResistrationRequest setUpGymResistrationRequest(){
        return GymResistrationRequest.builder()
                .gymName("Test Gym")
                .build();
    }

    @Test
    @DisplayName("[Service] 헬스장 등록 테스트")
    void addGymTest() {
        // given
        GymResistrationRequest request = setUpGymResistrationRequest();
        given(gymRepository.save(any(Gym.class))).willReturn(gym);
        given(managerRepository.findById(anyLong())).willReturn(Optional.of(manager));

        // when
        GymResponse response = gymService.addGym(request);

        // then
        verify(gymRepository).save(any(Gym.class));
        verify(managerRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getGymName()).isEqualTo(gym.getGymName());
    }

    @Test
    @DisplayName("[Service] 검색 키워드에 일치하는 헬스장 목록 조회 테스트")
    void findGymsByNameTest() {
        // given
        given(gymRepository.findByGymNameContaining(any(String.class))).willReturn(Collections.singletonList(gym));

        // when
        List<GymResponse> responses = gymService.findGymsByName("Test");

        // then
        verify(gymRepository).findByGymNameContaining(any(String.class));
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertThat(responses.get(0).getGymName()).isEqualTo(gym.getGymName());
    }

    @Test
    @DisplayName("[Service] 헬스장 정보 조회 테스트")
    void findGymTest() {
        // given
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        GymResponse response = gymService.findGym(1L);

        // then
        verify(gymRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getGymName()).isEqualTo(gym.getGymName());
    }

    @Test
    @DisplayName("[Service] 오픈채팅방 링크 조회 테스트")
    void getChatLinkTest() {
        // given
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        String chatLink = gymService.getChatLink(1L);

        // then
        verify(gymRepository).findById(anyLong());
        assertNotNull(chatLink);
    }

    @Test
    @DisplayName("[Service] 헬스장 사업자 정보 조회 테스트")
    void findGymRegistrationTest() {
        // given
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        GymRegistrationDto response = gymService.findGymRegistration(1L);

        // then
        verify(gymRepository).findById(anyLong());
        assertNotNull(response);
    }

    @Test
    @DisplayName("[Service] 헬스장 정보 수정 테스트")
    void modifyGymTest() {
        // given
        GymRequest gymRequest = GymRequest.builder().gymName("Updated Gym").build();
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        GymResponse response = gymService.modifyGym(1L, gymRequest);

        // then
        verify(gymRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getGymName()).isEqualTo("Updated Gym");
    }

    @Test
    @DisplayName("[Service] 오픈채팅방 링크 변경 테스트")
    void modifyChatLinkTest() {
        // given
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        GymResponse response = gymService.modifyChatLink(1L, "newLink");

        // then
        verify(gymRepository).findById(anyLong());
        assertNotNull(response);
    }

    @Test
    @DisplayName("[Service] 헬스장 삭제 테스트")
    void removeGymTest() {
        // given
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));

        // when
        gymService.removeGym(1L);

        // then
        verify(gymRepository).findById(anyLong());
        verify(gymRepository).delete(any(Gym.class));
    }
}
