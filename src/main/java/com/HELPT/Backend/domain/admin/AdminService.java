package com.HELPT.Backend.domain.admin;

import com.HELPT.Backend.domain.admin.dto.AdminRequest;
import com.HELPT.Backend.domain.manager.Manager;
import com.HELPT.Backend.domain.manager.dto.ManagerRequest;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import com.HELPT.Backend.global.auth.jwt.JWTToken;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public JWTResponse login(AdminRequest adminRequest) {
        try {
            String loginId = adminRequest.getLoginId();
            String password = adminRequest.getPassword();
            log.info(loginId);
            log.info(password);

            Admin admin = adminRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginId));

            // 비밀번호 검증
            if(!password.equals(admin.getPassword())){
                throw new BadCredentialsException("Invalid password");
            }
//            if (!passwordEncoder.matches(password, admin.getPassword())) {
//                throw new BadCredentialsException("Invalid password");
//            }

            // JWT 토큰 생성
            JWTToken jwt = jwtUtil.createTokens(admin.getAdminId());
            return JWTResponse.builder().token(jwt).build();
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.info("Login error", e);
            throw e;
        }
    }

}
