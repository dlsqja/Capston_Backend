package com.HELPT.Backend.global.kakaomodule;

import com.HELPT.Backend.domain.membership.MembershipService;
import com.HELPT.Backend.domain.membership.dto.PaymentRequest;
import com.HELPT.Backend.domain.product.ProductService;
import com.HELPT.Backend.domain.product.dto.ProductResponse;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final MembershipService membershipService;
    private final ProductService productService;

    /**
     * 결제요청
     */
    @PostMapping("/ready/{productId}")
    public ResponseEntity<KakaoReadyResponse> readyToKakaoPay(@PathVariable Long productId) {
        ProductResponse product = productService.findProduct(productId);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .userId(getCurrentUserId())
                .productId(productId)
                .productName(product.getMonths()+"개월 회원권")
                .price(product.getPrice())
                .build();
        kakaoPayService.kakaoPayReady(paymentRequest);
        return ResponseEntity.ok(kakaoPayService.kakaoPayReady(paymentRequest));
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponse> afterPayRequest(@RequestParam("pg_token") String pgToken) throws JsonProcessingException {
        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);
        membershipService.addMembership(Long.valueOf(kakaoApprove.getPartner_user_id()),Long.valueOf(kakaoApprove.getPartner_order_id()));
        return ResponseEntity.ok(kakaoApprove);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel() {
        throw new CustomException(ErrorCode.PAY_CANCEL);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public void fail() {
        throw new CustomException(ErrorCode.PAY_FAILED);
    }
}