package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.global.common.dto.KakaoLoginRequest;
import com.HELPT.Backend.domain.manager.dto.ManagerRequest;
import com.HELPT.Backend.domain.manager.dto.ManagerResponse;
import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import com.HELPT.Backend.global.auth.jwt.JWTToken;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private JWTUtil jwtUtil;

    private Manager manager;
    private ManagerRequest managerRequest;
    private JWTToken jwtToken;

    @BeforeEach
    void setUp() {
        manager = Manager.builder()
                .managerId(1L)
                .kakaoId("kakao123")
                .build();

        managerRequest = ManagerRequest.builder()
                .kakaoId("kakao123")
                .build();

        jwtToken = JWTToken.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();
    }

    @Test
    @DisplayName("[Service] 소셜 로그인 테스트")
    void socialLoginTest() {
        // given
        KakaoLoginRequest kakaoLoginRequest = KakaoLoginRequest.builder()
                .kakaoId("kakao123")
                .deviceToken("deviceToken")
                .build();
        given(managerRepository.findByKakaoId(anyString())).willReturn(Optional.of(manager));
        given(jwtUtil.createTokens(anyLong())).willReturn(jwtToken);

        // when
        JWTResponse response = managerService.login(kakaoLoginRequest);

        // then
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        verify(managerRepository).findByKakaoId(anyString());
        verify(jwtUtil).createTokens(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 로그인 테스트")
    void managerLoginTest() {
        // given
        KakaoLoginRequest kakaoLoginRequest = KakaoLoginRequest.builder()
                .kakaoId("kakao123")
                .deviceToken("deviceToken")
                .build();
        given(managerRepository.findByKakaoId(anyString())).willReturn(Optional.of(manager));
        given(jwtUtil.createTokens(anyLong())).willReturn(jwtToken);

        // when
        JWTResponse response = managerService.login(kakaoLoginRequest);

        // then
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        verify(managerRepository).findByKakaoId(anyString());
        verify(jwtUtil).createTokens(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 회원 가입 테스트")
    void registerManagerTest() {
        // given
        given(managerRepository.findByKakaoId(anyString())).willReturn(Optional.empty());
        given(managerRepository.save(any(Manager.class))).willReturn(manager);
        given(managerRepository.findByKakaoId(anyString())).willReturn(Optional.of(manager));
        given(jwtUtil.createTokens(anyLong())).willReturn(jwtToken);

        // when
        JWTResponse response = managerService.register(managerRequest);

        // then
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        verify(managerRepository).save(any(Manager.class));
        verify(managerRepository).findByKakaoId(anyString());
        verify(jwtUtil).createTokens(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 로그아웃 테스트")
    void logoutManagerTest() {
        // 추가적으로 로그아웃 테스트 코드 작성 필요
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 회원탈퇴 테스트")
    void removeManagerTest() {
        // given
        given(managerRepository.findById(anyLong())).willReturn(Optional.of(manager));

        // when
        managerService.removeManager(1L);

        // then
        verify(managerRepository).findById(anyLong());
        verify(managerRepository).delete(any(Manager.class));
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 목록 조회 테스트")
    void findManagersTest() {
        // given
        given(managerRepository.findAll()).willReturn(Collections.singletonList(manager));

        // when
        List<ManagerResponse> responses = managerService.findManagers();

        // then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(managerRepository).findAll();
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 상세 조회 테스트")
    void findManagerTest() {
        // given
        given(managerRepository.findById(anyLong())).willReturn(Optional.of(manager));

        // when
        ManagerResponse response = managerService.findManager(1L);

        // then
        assertNotNull(response);
        assertEquals(manager.getGym().getId(), response.getGymId());
        verify(managerRepository).findById(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자 수정 테스트")
    void modifyManagerTest() {
        // 추가적으로 수정 테스트 코드 작성 필요
    }
}
