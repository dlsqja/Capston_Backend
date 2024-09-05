package com.HELPT.Backend.domain.kakao_pay;

import com.HELPT.Backend.domain.membership.dto.PaymentRequest;
import com.HELPT.Backend.global.kakaomodule.KakaoApproveResponse;
import com.HELPT.Backend.global.kakaomodule.KakaoPayService;
import com.HELPT.Backend.global.kakaomodule.KakaoReadyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KakaoPayServiceTest {

    @InjectMocks
    private KakaoPayService kakaoPayService;

    @Mock
    private RestTemplate restTemplate;

    private PaymentRequest paymentRequest;
    private KakaoReadyResponse kakaoReadyResponse;
    private KakaoApproveResponse kakaoApproveResponse;

    @BeforeEach
    void setUp() {
        paymentRequest = PaymentRequest.builder()
                .userId(1L)
                .productId(1L)
                .productName("Test Product")
                .price(1000)
                .build();

        kakaoReadyResponse = new KakaoReadyResponse();
        kakaoReadyResponse.setTid("test_tid");

        kakaoApproveResponse = new KakaoApproveResponse();
        kakaoApproveResponse.setAid("test_aid");
    }

    @Test
    @DisplayName("[Service] 헬스장 상품 결제 요청")
    void a() {
        // given
        given(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(KakaoReadyResponse.class)))
                .willReturn(kakaoReadyResponse);

        // when
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady(paymentRequest);

        // then
        verify(restTemplate).postForObject(anyString(), any(HttpEntity.class), eq(KakaoReadyResponse.class));
        assertNotNull(response);
        assertEquals("test_tid", response.getTid());
    }

    @Test
    @DisplayName("[Service] 결제 성공 테스트")
    void b() {
        // given
        given(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(KakaoApproveResponse.class)))
                .willReturn(kakaoApproveResponse);

        // when
        KakaoApproveResponse response = kakaoPayService.approveResponse("test_pg_token");

        // then
        verify(restTemplate).postForObject(anyString(), any(HttpEntity.class), eq(KakaoApproveResponse.class));
        assertNotNull(response);
        assertEquals("test_aid", response.getAid());
    }

    @Test
    @DisplayName("[Service] 결제 실패 테스트")
    void c() {
        // given
        given(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(KakaoApproveResponse.class)))
                .willThrow(new RuntimeException("Payment failed"));

        // when & then
        assertThrows(RuntimeException.class, () -> kakaoPayService.approveResponse("invalid_pg_token"));
    }

    @Test
    @DisplayName("[Service] 결제 취소 테스트")
    void d() {
        // Implement the test for canceling the payment if needed
        // Currently, there is no such method in the provided service
    }
}
