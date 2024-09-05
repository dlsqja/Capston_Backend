package com.HELPT.Backend.domain.admin;

import com.HELPT.Backend.domain.admin.dto.AdminRequest;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import com.HELPT.Backend.global.auth.jwt.JWTToken;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JWTUtil jwtUtil;

    private Admin admin;
    private AdminRequest adminRequest;
    private JWTToken jwtToken;

    @BeforeEach
    void setUp() {
        admin = Admin.builder()
                .adminId(1L)
                .loginId("admin")
                .password("password")
                .build();

        adminRequest = AdminRequest.builder()
                .loginId("admin")
                .password("password")
                .build();

        jwtToken = JWTToken.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    @Test
    @DisplayName("[Service] 로그인 성공 테스트")
    void loginSuccessTest() {
        // given
        given(adminRepository.findByLoginId(anyString())).willReturn(Optional.of(admin));
        given(jwtUtil.createTokens(Long.valueOf(anyString()))).willReturn(jwtToken);

        // when
        JWTResponse response = adminService.login(adminRequest);

        // then
        verify(adminRepository).findByLoginId(anyString());
        verify(jwtUtil).createTokens(Long.valueOf(anyString()));
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
    }

    @Test
    @DisplayName("[Service] 로그인 실패 테스트 - 사용자 없음")
    void loginUserNotFoundTest() {
        // given
        given(adminRepository.findByLoginId(anyString())).willReturn(Optional.empty());

        // when
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> adminService.login(adminRequest));

        // then
        verify(adminRepository).findByLoginId(anyString());
        assertNotNull(exception);
    }

    @Test
    @DisplayName("[Service] 로그인 실패 테스트 - 잘못된 비밀번호")
    void loginInvalidPasswordTest() {
        // given
        adminRequest.setPassword("wrongPassword");
        given(adminRepository.findByLoginId(anyString())).willReturn(Optional.of(admin));

        // when
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> adminService.login(adminRequest));

        // then
        verify(adminRepository).findByLoginId(anyString());
        assertNotNull(exception);
    }
}
