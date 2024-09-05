package com.HELPT.Backend.domain.membership.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRequest {

    private Long userId;
    private Long productId;
    private String productName;
    private int price;

}
