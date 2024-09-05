package com.HELPT.Backend.domain.membership;

import com.HELPT.Backend.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepositoryCustom {

    public Product findProduct(Long productId);
}
