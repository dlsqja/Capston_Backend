package com.HELPT.Backend.domain.product.dto;

import com.HELPT.Backend.domain.manager.Manager;
import com.HELPT.Backend.domain.product.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequest {

    private int months;

    private int price;

    @Builder
    public ProductRequest(int months, int price) {
        this.months = months;
        this.price = price;
    }

}
