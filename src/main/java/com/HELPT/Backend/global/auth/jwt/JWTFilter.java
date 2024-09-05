package com.HELPT.Backend.global.auth.jwt;

import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.global.auth.dto.CustomUserDetails;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RequestAttributeSecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("jwt");
        String token = jwtUtil.getJwtFromRequest(request);
        if(jwtUtil.isExpired(token)){
            throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }
        //토큰에서 username과 role 획득
        Long userId = jwtUtil.getUserId(token);
        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails userDetails = new CustomUserDetails(userId);
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        filterChain.doFilter(request, response);
    }
}
