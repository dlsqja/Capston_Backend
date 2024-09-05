package com.HELPT.Backend.global.kakaomodule;

import com.HELPT.Backend.domain.membership.MembershipRepository;
import com.HELPT.Backend.domain.membership.dto.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoPayService {

//    @Value("${kakaopay.cid}")
    private String cid = "TC0ONETIME"; // 가맹점 테스트 코드
//    @Value("${kakaopay.admin-key}")
    private String admin_Key = "DEV536BD84D06BAA55E2D09B0C2F7BE1837FF284";// 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
    private KakaoReadyResponse kakaoReady;
    private PaymentRequest kakaoRequest;

    private final MembershipRepository membershipRepository;

    public KakaoReadyResponse kakaoPayReady(PaymentRequest paymentRequest){
        // 카카오페이 요청 양식
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", paymentRequest.getProductId().toString());
        parameters.put("partner_user_id",paymentRequest.getUserId().toString());
        parameters.put("item_name", paymentRequest.getProductName());
        parameters.put("quantity", 1);
        parameters.put("total_amount", paymentRequest.getPrice());
        parameters.put("tax_free_amount", 100);
        parameters.put("approval_url", "http://54.180.211.41:8080/payment/success"); // 성공 시 redirect url
        parameters.put("cancel_url", "http://54.180.211.41:8080/payment/cancel"); // 취소 시 redirect url
        parameters.put("fail_url", "http://54.180.211.41:8080/payment/fail"); // 실패 시 redirect url
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 파라미터, 헤더
        HttpEntity<String> requestEntity = new HttpEntity<>(json, this.getHeaders());
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        log.info("결재 오류");
        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);
        kakaoRequest = paymentRequest;
        log.info("결재 성공");
        return kakaoReady;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoApproveResponse approveResponse(String pgToken){
        Map<String, Object> parameters = new HashMap<>();
        // 카카오 요청
        parameters.put("cid", cid);
        parameters.put("tid", kakaoReady.getTid());
        parameters.put("partner_order_id", kakaoRequest.getProductId().toString());
        parameters.put("partner_user_id", kakaoRequest.getUserId().toString());
        parameters.put("pg_token", pgToken);
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 파라미터, 헤더
        HttpEntity<String> requestEntity = new HttpEntity<>(json, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        log.info("결제 처리중");
        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);
        log.info("결제 승인 완료");
        return approveResponse;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "SECRET_KEY " + admin_Key;
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/json;charset=utf-8");

        return httpHeaders;
    }
}