package com.HELPT.Backend.global.auth.service;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import com.HELPT.Backend.global.auth.jwt.JWTToken;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final JWTUtil jwtUtil;

    public JWTResponse refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtUtil.getJwtFromRequest(request);
        JWTToken jwt = jwtUtil.refresh(refreshToken);
        return JWTResponse.builder().token(jwt).build();
    }
}