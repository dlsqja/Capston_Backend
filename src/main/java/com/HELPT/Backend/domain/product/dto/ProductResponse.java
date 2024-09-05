package com.HELPT.Backend.domain.product.dto;

import com.HELPT.Backend.domain.product.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {
    private Long productId;
    private int months;
    private int price;
    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.months = product.getMonths();
        this.price = product.getPrice();
    }
}
